package com.trevorism.gcloud.webapi.service

import com.trevorism.data.model.paging.Page
import com.trevorism.data.service.lookup.LookupService
import com.trevorism.data.service.paging.InMemoryPagingService
import org.junit.jupiter.api.Test

class PagingServiceTest {

    @Test
    void testPaging(){
        InMemoryPagingService service = new InMemoryPagingService()
        service.lookupService = { createFakeData(10)} as LookupService

        def result = service.page(new Page(lookup: "datastore:alert", page: 2, pageSize: 3))
        assert result.size() == 3
    }

    @Test
    void testPagingLimit(){
        InMemoryPagingService service = new InMemoryPagingService()
        service.lookupService = {  createFakeData(10)} as LookupService

        def result = service.page(new Page(lookup: "datastore:alert", page: 0, pageSize: 0, limit: 4))
        assert result.size() == 4
    }

    @Test
    void testPagingBoth(){
        InMemoryPagingService service = new InMemoryPagingService()
        service.lookupService = {  createFakeData(10)} as LookupService

        def result = service.page(new Page(lookup: "datastore:alert", page: 2, pageSize: 5, limit: 4))
        assert result.size() == 5
    }

    @Test
    void testPagingOutOfBounds(){
        InMemoryPagingService service = new InMemoryPagingService()
        service.lookupService = {  createFakeData(10)} as LookupService

        def result = service.page(new Page(lookup: "datastore:alert", page: 5, pageSize: 3))
        assert result.size() == 0
    }

    @Test
    void testPagingNearEnd(){
        InMemoryPagingService service = new InMemoryPagingService()
        service.lookupService = {  createFakeData(10)} as LookupService

        def result = service.page(new Page(lookup: "datastore:alert", page: 4, pageSize: 3))
        assert result.size() == 1
    }

    @Test
    void testEmptyPaging(){
        InMemoryPagingService service = new InMemoryPagingService()
        service.lookupService = { createFakeData(10)} as LookupService

        def result = service.page(new Page())
        assert result.size() == 10
    }


    private List<Map> createFakeData(int count){
        def list = []
        count.times{list << [id:it] }
        return list
    }
}
