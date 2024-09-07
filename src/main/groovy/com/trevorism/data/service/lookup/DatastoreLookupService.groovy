package com.trevorism.data.service.lookup

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trevorism.data.DataUtils
import com.trevorism.data.model.SingleDatasourceRequest
import com.trevorism.data.model.exception.InvalidLookupException
import com.trevorism.https.SecureHttpClient

class DatastoreLookupService implements LookupService {

    private SecureHttpClient client
    private Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
    private static final String DATASTORE_OBJECT_BASE_URL = "${DataUtils.DATASTORE_BASE_URL}/object"
    private static final String BIGQUERY_OBJECT_BASE_URL = "${DataUtils.BIGQUERY_BASE_URL}/object"
    private static final String MEMORY_OBJECT_BASE_URL = "${DataUtils.MEMORY_BASE_URL}/object"

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

        String url = "$DATASTORE_OBJECT_BASE_URL/$kind"
        if (datasource == "bigquery")
            url = "$BIGQUERY_OBJECT_BASE_URL/$kind"
        if (datasource == "memory")
            url = "$MEMORY_OBJECT_BASE_URL/$kind"

        String json = client.get(url)
        return gson.fromJson(json, List)
    }
}
