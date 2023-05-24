package com.trevorism.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class DataUtils {
    static final String DATASTORE_BASE_URL = "https://datastore.data.trevorism.com"
    static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()

    static def getById(String id, Class clazz) {
        return gson.fromJson("${DATASTORE_BASE_URL}/object/${clazz.simpleName}/${id}", clazz)
    }
}
