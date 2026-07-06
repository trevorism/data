package com.trevorism.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trevorism.https.SecureHttpClient

class DataUtils {
    static final String DATASTORE_BASE_URL = "https://datastore.data.trevorism.com"
    static final String BIGQUERY_BASE_URL = "https://bigquery.data.trevorism.com"
    static final String MEMORY_BASE_URL = "https://memory.data.trevorism.com"

    static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()

    static def getById(SecureHttpClient httpClient, String id, Class clazz) {
        String json = httpClient.get("${DATASTORE_BASE_URL}/object/${clazz.simpleName}/${id}")
        return gson.fromJson(json, clazz)
    }
}
