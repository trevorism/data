package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trevorism.data.model.searching.Search
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

When(/a search of {string} is requested/) { String string ->
    Search search = new Search(lookup: "datastore:arbitrary", query: string)
    String jsonList = secureHttpClient.post("https://data.trevorism.com/search", gson.toJson(search))
    list = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>() {}.getType())
}

Then(/the object is found/) { ->
    assert list
    assert list[0]
    assert list[0].name == "object1"
}

Then(/no objects are found/) { ->
    assert !list
}