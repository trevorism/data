package com.trevorism.gcloud.webapi.service.lookup

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trevorism.gcloud.webapi.model.SingleDatasourceRequest
import com.trevorism.gcloud.webapi.model.exception.InvalidLookupException
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient

class DatastoreLookupService implements LookupService{

    private SecureHttpClient client = new DefaultSecureHttpClient()
    private Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
    private final static String DATASTORE_BASE_URL = "https://datastore.trevorism.com/api"

    @Override
    List<Map<String, Object>> lookupDataset(SingleDatasourceRequest request) {
        def parts = request.lookup?.split(":")

        if(!parts || parts.size() < 2)
            throw new InvalidLookupException("Unable to lookup dataset from: $request.lookup")

        String kind = parts[1]

        String json = client.get("$DATASTORE_BASE_URL/$kind")
        return gson.fromJson(json, List)
    }
}
