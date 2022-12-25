package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trevorism.gcloud.webapi.model.paging.Page
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

SecureHttpClient secureHttpClient = new DefaultSecureHttpClient()
String json
Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

When(/a single data source request is invoked with a valid lookup locator/) {  ->
    Page page = new Page(lookup: ":arbitrary", pageSize: 10, page: 1)
    String jsonList = secureHttpClient.post("https://data.trevorism.com/page", gson.toJson(page))
    list = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>() {}.getType())
}

When(/a single data source request is invoked with a valid invalid locator/) {  ->
    Page page = new Page(lookup: "arbitrary", pageSize: 10, page: 1)
    json = secureHttpClient.post("https://data.trevorism.com/page", gson.toJson(page))
}

Then(/objects are found/) {  ->
    assert list
}

Then(/an error is returned/) {  ->
    assert json.contains("failed")
}