package com.trevorism.gcloud.webapi.service.lookup

import com.trevorism.data.model.exception.InvalidLookupException
import com.trevorism.data.model.searching.Search
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class DatastoreLookupServiceTest {

    @Test
    void testLookup(){
        DatastoreLookupService service = new DatastoreLookupService()
        service.client = [get:{'[{"id":4}]'}] as SecureHttpClient
        def result = service.lookupDataset(new Search(lookup: "datastore:blah"))

        assert result
        assert result[0].id == 4

    }

    @Test
    void testInvalidLookup(){
        DatastoreLookupService service = new DatastoreLookupService()
        service.client = [get:{'[{"id":4}]'}] as SecureHttpClient
        assertThrows(InvalidLookupException, () -> service.lookupDataset(new Search(lookup: "blah")))
    }

    @Test
    void testInvalidLookupNull(){
        DatastoreLookupService service = new DatastoreLookupService()
        service.client = [get:{'[{"id":4}]'}] as SecureHttpClient
        assertThrows(InvalidLookupException, () -> service.lookupDataset(new Search(lookup: "")))

    }
}
