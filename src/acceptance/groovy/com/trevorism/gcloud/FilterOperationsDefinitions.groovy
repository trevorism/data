package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.model.aggregating.AggregationConstants
import com.trevorism.gcloud.webapi.model.aggregating.AggregationFunction
import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.filtering.SimpleFilter
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient

import java.time.Instant
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

SecureHttpClient secureHttpClient = new DefaultSecureHttpClient()
List list = []
Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

When(/a filter request with date before {int} seconds from now is requested/) { Integer int1 ->
    String dateValue = Instant.now().minusSeconds(int1).truncatedTo( ChronoUnit.SECONDS ).toString()
    ComplexFilter filter = new ComplexFilter(lookup: "datastore:arbitrary", simpleFilters: [new SimpleFilter(field: "date", operator: "<", type: "date", value: dateValue)])
    String json = gson.toJson(filter)
    String jsonList = secureHttpClient.post("https://data.trevorism.com/filter", json)
    list = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>() {}.getType())
}

Then(/the filtered objects are returned/) {  ->
    assert list
    assert list[0]
    assert list[0].name == "object2"
    assert list[0].number == 12
    assert list[0].decimal == 12

}