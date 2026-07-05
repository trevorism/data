package com.trevorism.gcloud.webapi.service.request

import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.combining.Join
import com.trevorism.data.model.combining.SetOperation
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.model.paging.Page
import com.trevorism.data.model.searching.Search
import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.model.sorting.Sort
import com.trevorism.data.model.transferring.Transfer
import com.trevorism.data.service.request.OperationRequestFactory
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class OperationRequestFactoryTest {

    private OperationRequestFactory factory = new OperationRequestFactory()

    @Test
    void testBuildAggregation() {
        def result = factory.buildRequestObject([groupBy: ["state"], functions: []])
        assert result instanceof Aggregation
        assert result.groupBy == ["state"]
    }

    @Test
    void testBuildComplexFilter() {
        def result = factory.buildRequestObject([simpleFilters: [], complexFilters: []])
        assert result instanceof ComplexFilter
    }

    @Test
    void testBuildPage() {
        def result = factory.buildRequestObject([page: 2, pageSize: 25, limit: 100])
        assert result instanceof Page
        assert result.page == 2
        assert result.pageSize == 25
        assert result.limit == 100
    }

    @Test
    void testBuildSearch() {
        def result = factory.buildRequestObject([query: "hello world"])
        assert result instanceof Search
        assert result.query == "hello world"
    }

    @Test
    void testBuildComplexSort() {
        def result = factory.buildRequestObject([sorts: [new Sort(field: "name", descending: true)]])
        assert result instanceof ComplexSort
        assert result.sorts.size() == 1
        assert result.sorts[0].field == "name"
    }

    @Test
    void testBuildTransfer() {
        def result = factory.buildRequestObject([singleRepositoryLookup: "a", destinationRepositoryLookup: "b"])
        assert result instanceof Transfer
        assert result.singleRepositoryLookup == "a"
        assert result.destinationRepositoryLookup == "b"
    }

    @Test
    void testBuildSetOperation() {
        def result = factory.buildRequestObject([datasetUrls: ["https://one", "https://two"]])
        assert result instanceof SetOperation
        assert result.datasetUrls.size() == 2
    }

    @Test
    void testBuildJoin() {
        def result = factory.buildRequestObject([joinFields: []])
        assert result instanceof Join
    }

    @Test
    void testUnknownRequestThrows() {
        assertThrows(RuntimeException) {
            factory.buildRequestObject([someUnrecognizedField: "value"])
        }
    }

    @Test
    void testEmptyRequestDefaultsToAggregation() {
        def result = factory.buildRequestObject([:])
        assert result instanceof Aggregation
    }

    @Test
    void testTypeOnlyRoutesToComplexFilter() {
        def result = factory.buildRequestObject([type: "union"])
        assert result instanceof ComplexFilter
    }
}
