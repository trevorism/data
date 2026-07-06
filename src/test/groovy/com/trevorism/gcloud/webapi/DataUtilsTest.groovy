package com.trevorism.gcloud.webapi

import com.trevorism.data.DataUtils
import com.trevorism.data.model.Query
import com.trevorism.data.model.searching.Search
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

class DataUtilsTest {

    @Test
    void testGetByIdParsesResponse() {
        SecureHttpClient client = [get: { String url -> '{"id":"123","lookup":"datastore:Person","query":"name"}' }] as SecureHttpClient

        Search result = DataUtils.getById(client, "123", Search)

        assert result instanceof Search
        assert result.id == "123"
        assert result.lookup == "datastore:Person"
        assert result.query == "name"
    }

    @Test
    void testGetByIdBuildsDatastoreUrlFromClassAndId() {
        String requestedUrl = null
        SecureHttpClient client = [get: { String url -> requestedUrl = url; '{}' }] as SecureHttpClient

        DataUtils.getById(client, "abc", Query)

        assert requestedUrl == "${DataUtils.DATASTORE_BASE_URL}/object/Query/abc"
    }
}
