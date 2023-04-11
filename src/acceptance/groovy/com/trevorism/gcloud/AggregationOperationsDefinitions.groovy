package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.aggregating.AggregationConstants
import com.trevorism.data.model.aggregating.AggregationFunction
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient

/**
 * @author tbrooks
 */

this.metaClass.mixin(io.cucumber.groovy.Hooks)
this.metaClass.mixin(io.cucumber.groovy.EN)

SecureHttpClient secureHttpClient = new DefaultSecureHttpClient()
List list = []
Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

When(/an aggregation request of by number and sum of decimal field is requested/) {  ->
    Aggregation aggregation = new Aggregation(lookup: "datastore:arbitrary", groupBy: ["number"], functions: [new AggregationFunction(field: "decimal", functionName: AggregationConstants.SUM)])
    String jsonList = secureHttpClient.post("https://data.trevorism.com/aggregation", gson.toJson(aggregation))
    list = gson.fromJson(jsonList, List)
}

Then(/the aggregation result is returned/) {  ->
    assert list
    assert list[0]
    assert list[0]["number"] == 12
    assert list[0]["sum_decimal"] == 10.7
}