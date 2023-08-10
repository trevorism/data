package com.trevorism.gcloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.trevorism.https.AppClientSecureHttpClient
import com.trevorism.https.SecureHttpClient

import java.time.Instant

/**
 * @author tbrooks
 */

this.metaClass.mixin(io.cucumber.groovy.Hooks)
this.metaClass.mixin(io.cucumber.groovy.EN)

SecureHttpClient secureHttpClient = new AppClientSecureHttpClient()
Arbitrary dataObject1
Arbitrary dataObject2
List list = []
Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

Given(/two sample objects are created/) {  ->
    def obj1 = new Arbitrary(name: "object1", date: Date.from(Instant.now()), decimal: 4.2, number: 12)
    def obj2 = new Arbitrary(name: "object2", date: Date.from(Instant.now().minusSeconds(60)), decimal: 6.5, number: 12)

    String json1 = secureHttpClient.post("https://data.trevorism.com/object/arbitrary", gson.toJson(obj1))
    String json2 = secureHttpClient.post("https://data.trevorism.com/object/arbitrary", gson.toJson(obj2))

    dataObject1 = gson.fromJson(json1, Arbitrary)
    dataObject2 = gson.fromJson(json2, Arbitrary)
}

When(/a list of sample objects are requested/) {  ->
    String jsonList = secureHttpClient.get("https://data.trevorism.com/object/arbitrary")
    list = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>(){}.getType())
}

Then(/two sample objects are found/) {  ->
    assert list
    assert list.size() >= 2

    def first = list.find { it.name == "object1"}
    def second = list.find { it.name == "object2"}

    assert first.name == "object1"
    assert first.number == 12
    assert first.decimal == 4.2

    assert second.name == "object2"
    assert second.number == 12
    assert second.decimal == 6.5

}

Given(/the data is cleared/) {  ->
    String listJson = secureHttpClient.get("https://data.trevorism.com/object/arbitrary")
    gson.fromJson(listJson, new TypeToken<List<Arbitrary>>(){}.getType()).each{ Arbitrary obj ->
        secureHttpClient.delete("https://data.trevorism.com/object/arbitrary/${obj.id}")
    }
}

Then(/the two sample objects can be deleted/) {  ->
    String listJson = secureHttpClient.get("https://data.trevorism.com/object/arbitrary")
    gson.fromJson(listJson, new TypeToken<List<Arbitrary>>(){}.getType()).each{ Arbitrary obj ->
        secureHttpClient.delete("https://data.trevorism.com/object/arbitrary/${obj.id}")
    }
    String jsonList = secureHttpClient.get("https://data.trevorism.com/object/arbitrary")
    def result = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>(){}.getType())
    assert !(result)
}

When(/the objects are updated/) {  ->
    dataObject1.decimal = 92.2
    dataObject1.name = "changed"
    dataObject1.number = 4

    dataObject2.name = "different"
    dataObject2.number = 0
    dataObject2.date = null

    secureHttpClient.put("https://data.trevorism.com/object/arbitrary/${dataObject1.id}", gson.toJson(dataObject1))
    secureHttpClient.put("https://data.trevorism.com/object/arbitrary/${dataObject2.id}", gson.toJson(dataObject2))
}

Then(/the objects reflect the updates/) {  ->
    String jsonList = secureHttpClient.get("https://data.trevorism.com/object/arbitrary")
    def result = gson.fromJson(jsonList, new TypeToken<List<Arbitrary>>(){}.getType())
    Arbitrary obj1 = result.find{ it.id == dataObject1.id}
    assert obj1.name == "changed"
    assert obj1.decimal == 92.2
    assert obj1.number == 4
    assert obj1.date


    Arbitrary obj2 = result.find{ it.id == dataObject2.id}

    assert obj2.name == "different"
    assert obj2.decimal == 6.5
    assert obj2.number == 0
    assert !obj2.date

}