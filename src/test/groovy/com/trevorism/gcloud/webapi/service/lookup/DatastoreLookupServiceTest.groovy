package com.trevorism.gcloud.webapi.service.lookup

import com.trevorism.gcloud.webapi.model.searching.Search
import com.trevorism.https.SecureHttpClient
import org.junit.Test

class DatastoreLookupServiceTest {

    @Test
    void testLookup(){
        DatastoreLookupService service = new DatastoreLookupService()
        service.client = [get:{'[{"id":4}]'}] as SecureHttpClient
        def result = service.lookupDataset(new Search(lookup: "datastore:blah"))

        assert result
        assert result[0].id == 4

    }

    @Test(expected = LookupException)
    void testInvalidLookup(){
        DatastoreLookupService service = new DatastoreLookupService()
        service.client = [get:{'[{"id":4}]'}] as SecureHttpClient
        service.lookupDataset(new Search(lookup: "blah"))

    }
}
