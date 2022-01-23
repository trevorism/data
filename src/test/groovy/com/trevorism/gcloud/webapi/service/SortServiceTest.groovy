package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.sorting.ComplexSort
import com.trevorism.gcloud.webapi.model.sorting.Sort
import com.trevorism.gcloud.webapi.service.lookup.LookupService
import com.trevorism.gcloud.webapi.service.sorting.InMemorySortService
import org.junit.Test

class SortServiceTest {


    @Test
    void testSimpleSorting(){
        InMemorySortService service = new InMemorySortService()
        service.lookupService = {createFakeData()} as LookupService
        def result = service.sort(new ComplexSort(lookup: "datastore:button", sorts: [new Sort(descending: false, field: "id")]))

        assert result[0].id == 1
        assert result[1].id == 2
        assert result[2].id == 3
        assert result[3].id == 4

    }

    @Test
    void testSimpleSortingDescending(){
        InMemorySortService service = new InMemorySortService()
        service.lookupService = {createFakeData()} as LookupService
        def result = service.sort(new ComplexSort(lookup: "datastore:button", sorts: [new Sort(descending: true, field: "id")]))

        assert result[0].id == 4
        assert result[1].id == 3
        assert result[2].id == 2
        assert result[3].id == 1
    }

    @Test
    void testComplexSorting(){
        InMemorySortService service = new InMemorySortService()
        service.lookupService = {createFakeData()} as LookupService
        def result = service.sort(new ComplexSort(lookup: "datastore:button", sorts: [new Sort(descending: true, field: "age"),new Sort(descending: false, field: "name") ]))

        assert result[0].id == 4
        assert result[1].id == 3
        assert result[2].id == 1
        assert result[3].id == 2
    }

    @Test
    void testComplexSorting2(){
        InMemorySortService service = new InMemorySortService()
        service.lookupService = {createFakeData()} as LookupService
        def result = service.sort(new ComplexSort(lookup: "datastore:button", sorts: [new Sort(descending: false, field: "age"),new Sort(descending: true, field: "name") ]))

        assert result[0].id == 2
        assert result[1].id == 1
        assert result[2].id == 3
        assert result[3].id == 4
    }

    @Test
    void testComplexSortingCaseInsensitive(){
        InMemorySortService service = new InMemorySortService()
        service.lookupService = {createFakeData()} as LookupService
        def result = service.sort(new ComplexSort(lookup: "datastore:button", sorts: [new Sort(descending: false, field: "AgE"),new Sort(descending: true, field: "NAME") ]))

        assert result[0].id == 2
        assert result[1].id == 1
        assert result[2].id == 3
        assert result[3].id == 4
    }

    @Test
    void testComplexSortingNullFields(){
        InMemorySortService service = new InMemorySortService()
        service.lookupService = {createFakeData()} as LookupService
        def result = service.sort(new ComplexSort(lookup: "datastore:button", sorts: [new Sort(descending: false)]))

        assert result[0].id == 1
        assert result[1].id == 2
        assert result[2].id == 3
        assert result[3].id == 4
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
