package com.trevorism.gcloud.webapi.service

import com.trevorism.data.model.searching.Search
import com.trevorism.data.service.lookup.LookupService
import com.trevorism.data.service.search.InMemorySearchService
import org.junit.jupiter.api.Test

import java.time.Instant
import java.time.temporal.ChronoUnit

class SearchServiceTest {

    @Test
    void testSimpleSearch() {
        InMemorySearchService service = new InMemorySearchService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.search(new Search(query: "e"))

        assert result.size() == 2
        assert result[0].id == 1
        assert result[1].id == 3
    }

    @Test
    void testSimpleSearchMissing() {
        InMemorySearchService service = new InMemorySearchService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.search(new Search(query: "z"))

        assert !result
    }

    @Test
    void testEmpty() {
        InMemorySearchService service = new InMemorySearchService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.search(new Search())
        assert result
    }

    private List<Map> createFakeData() {
        def list = []
        list << [id: 1, age: 10, name: "Ren", birthday: Instant.now().minus(1, ChronoUnit.DAYS)]
        list << [id: 2, age: 7, name: "Stimpy", birthday: Instant.now().minus(0, ChronoUnit.DAYS)]
        list << [id: 3, age: 20, name: "Trev", birthday: Instant.now().minus(20, ChronoUnit.DAYS)]
        list << [id: 4, age: 22, name: "Ism", birthday: Instant.now().minus(22, ChronoUnit.DAYS)]
        return list
    }
}
