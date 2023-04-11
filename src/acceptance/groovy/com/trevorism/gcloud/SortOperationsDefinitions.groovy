package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.model.sorting.Sort
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

When(/a list of sorted sample objects are requested by name descending/) { ->
    ComplexSort sort = new ComplexSort(lookup: "datastore:arbitrary", sorts: [new Sort(field: "name", descending: true)])
    String jsonList = secureHttpClient.post("https://data.trevorism.com/sort", gson.toJson(sort))
    list = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>() {}.getType())
}

Then(/two sample objects are found in order/) { ->
    assert list.size() == 2
    assert list[0]
    assert list[0].name == "object2"
    assert list[1]
    assert list[1].name == "object1"
}