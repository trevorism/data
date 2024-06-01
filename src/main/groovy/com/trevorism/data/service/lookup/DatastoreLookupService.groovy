package com.trevorism.data.service.lookup

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trevorism.data.model.SingleDatasourceRequest
import com.trevorism.data.model.exception.InvalidLookupException
import com.trevorism.https.SecureHttpClient

class DatastoreLookupService implements LookupService {

    private SecureHttpClient client
    private Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
    private final static String DATASTORE_BASE_URL = "https://datastore.data.trevorism.com/object"
    private final static String BIGQUERY_BASE_URL = "https://bigquery.data.trevorism.com/object"

    DatastoreLookupService(SecureHttpClient client) {
        this.client = client
    }

    @Override
    List<Map<String, Object>> lookupDataset(SingleDatasourceRequest request) {
        def parts = request.lookup?.split(":")

        if (!parts || parts.size() < 2)
            throw new InvalidLookupException("Unable to lookup dataset from: $request.lookup")

        String datasource = parts[0]
        String kind = parts[1]

        String url = "$DATASTORE_BASE_URL/$kind"
        if (datasource == "bigquery")
            url = "$BIGQUERY_BASE_URL/$kind"

        String json = client.get(url)
        return gson.fromJson(json, List)
    }
}
