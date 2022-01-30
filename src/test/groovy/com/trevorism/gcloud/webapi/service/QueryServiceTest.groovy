package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.Query
import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.filtering.FilterConstants
import com.trevorism.gcloud.webapi.model.filtering.SimpleFilter
import com.trevorism.gcloud.webapi.model.searching.Search
import com.trevorism.gcloud.webapi.service.lookup.LookupService
import com.trevorism.gcloud.webapi.service.query.InMemoryQueryService
import org.junit.Test

class QueryServiceTest {

    @Test
    void testQuery() {
        InMemoryQueryService service = new InMemoryQueryService()
        service.lookupService = { createFakeData() } as LookupService

        assert service.query(new Query()).size() == 4
    }

    @Test
    void testQueryWithAggregation() {
        InMemoryQueryService service = new InMemoryQueryService()
        service.lookupService = { createFakeData() } as LookupService

        assert service.query(new Query(group: new Aggregation(groupBy: ["age"]))).size() == 3
    }

    @Test
    void testQueryWithSearch() {
        InMemoryQueryService service = new InMemoryQueryService()
        service.lookupService = { createFakeData() } as LookupService

        assert service.query(new Query(find: new Search(query: "e"))).size() == 2

    }

    @Test
    void testQueryWithEmpty() {
        InMemoryQueryService service = new InMemoryQueryService()
        service.lookupService = { createFakeData() } as LookupService

        assert service.query(new Query()).size() == 4
    }

    @Test
    void testQueryWithCompound() {
        InMemoryQueryService service = new InMemoryQueryService()
        service.lookupService = { createFakeData() } as LookupService

        assert service.query(new Query(group: new Aggregation(groupBy: ["age"]),
                where: new ComplexFilter(simpleFilters: [
                        new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_LESS_THAN, value: "21")
                ]))).size() == 2
    }

    private List<Map> createFakeData(){
        def list = []
        list << [id:1, age: 10, name: "Ren"]
        list << [id:2, age: 10, name: "Stimpy"]
        list << [id:3, age: 20, name: "Trev"]
        list << [id:4, age: 22, name: "Ism"]
        return list
    }
}
