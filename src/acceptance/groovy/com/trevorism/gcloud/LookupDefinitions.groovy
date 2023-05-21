package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trevorism.data.model.paging.Page
import com.trevorism.https.AppClientSecureHttpClient
import com.trevorism.https.SecureHttpClient

/**
 * @author tbrooks
 */

this.metaClass.mixin(io.cucumber.groovy.Hooks)
this.metaClass.mixin(io.cucumber.groovy.EN)

SecureHttpClient secureHttpClient = new AppClientSecureHttpClient()
String error
Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

When(/a single data source request is invoked with a valid lookup locator/) {  ->
    Page page = new Page(lookup: ":arbitrary", pageSize: 10, page: 1)
    String jsonList = secureHttpClient.post("https://data.trevorism.com/page", gson.toJson(page))
    list = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>() {}.getType())
}

When(/a single data source request is invoked with a valid invalid locator/) {  ->
    Page page = new Page(lookup: "arbitrary", pageSize: 10, page: 1)
    try{
        secureHttpClient.post("https://data.trevorism.com/page", gson.toJson(page))
    } catch(Exception e){
        error = e.message
    }
}

Then(/objects are found/) {  ->
    assert list
}

Then(/an error is returned/) {  ->
    assert error
}