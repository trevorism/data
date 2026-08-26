package com.trevorism.gcloud.webapi.service

import com.trevorism.data.model.Query
import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.aggregating.AggregationConstants
import com.trevorism.data.model.aggregating.AggregationFunction
import com.trevorism.data.model.exception.IncomparableFieldException
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.model.filtering.FilterConstants
import com.trevorism.data.model.filtering.SimpleFilter
import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.model.sorting.Sort
import com.trevorism.data.service.aggregate.InMemoryAggregationService
import com.trevorism.data.service.filter.InMemoryFilterService
import com.trevorism.data.service.lookup.LookupService
import com.trevorism.data.service.query.InMemoryQueryService
import com.trevorism.data.service.sort.InMemorySortService
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class StructuredAndCaseInsensitiveFieldTest {

    private static List<Map> camelCaseData() {
        return [[id: 1, numberOfTests: 5], [id: 2, numberOfTests: 50]]
    }

    private static List<Map> lowercasedData() {
        return [[id: 1, numberoftests: 5], [id: 2, numberoftests: 50]]
    }

    private static List<Map> structuredData() {
        return [[id: 1, tags: ["red", "blue"], meta: [color: "red"]],
                [id: 2, tags: ["green"], meta: [color: "green"]]]
    }

    @Test
    void testFilterMatchesCamelCaseField() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { camelCaseData() } as LookupService

        def result = service.filter(new ComplexFilter(simpleFilters: [new SimpleFilter(
                type: FilterConstants.TYPE_NUMBER, field: "numberOfTests", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "10")]))

        assert result.size() == 1
        assert result[0].id == 2
    }

    @Test
    void testFilterMatchesLowercasedFieldFromDatastore() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { lowercasedData() } as LookupService

        def result = service.filter(new ComplexFilter(simpleFilters: [new SimpleFilter(
                type: FilterConstants.TYPE_NUMBER, field: "numberOfTests", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "10")]))

        assert result.size() == 1
        assert result[0].id == 2
    }

    @Test
    void testSortMatchesCamelCaseField() {
        InMemorySortService service = new InMemorySortService()
        service.lookupService = { camelCaseData() } as LookupService

        def result = service.sort(new ComplexSort(sorts: [new Sort(field: "numberOfTests", descending: true)]))

        assert result[0].id == 2
        assert result[1].id == 1
    }

    @Test
    void testProjectionMatchesLowercasedFieldFromDatastore() {
        InMemoryQueryService service = new InMemoryQueryService(null)
        service.lookupService = { lowercasedData() } as LookupService

        def result = service.query(new Query(fields: ["numberOfTests"]))

        assert result[0].numberOfTests == 5
        assert result[1].numberOfTests == 50
    }

    @Test
    void testAggregationMatchesCamelCaseField() {
        InMemoryAggregationService service = new InMemoryAggregationService()
        service.lookupService = { [[id: 1, teamName: "a", score: 3], [id: 2, teamName: "a", score: 7]] } as LookupService

        def result = service.aggregate(new Aggregation(groupBy: ["teamname"],
                functions: [new AggregationFunction(field: "score", functionName: AggregationConstants.SUM)]))

        assert result.size() == 1
        assert result[0].sum_score == 10
    }

    @Test
    void testEqualityOnListValuedFieldIsRejected() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { structuredData() } as LookupService

        assertThrows(IncomparableFieldException) {
            service.filter(new ComplexFilter(simpleFilters: [new SimpleFilter(
                    field: "tags", operator: FilterConstants.OPERATOR_EQUAL, value: "green")]))
        }
    }

    @Test
    void testFilterOnJsonTextFromDatastoreStillCompares() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { [[id: 1, tags: '["red","blue"]'], [id: 2, tags: '["green"]']] } as LookupService

        def result = service.filter(new ComplexFilter(simpleFilters: [new SimpleFilter(
                field: "tags", operator: FilterConstants.OPERATOR_EQUAL, value: '["green"]')]))

        assert result.size() == 1
        assert result[0].id == 2
    }

    @Test
    void testRelationalOperatorOnListValuedFieldIsRejected() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { structuredData() } as LookupService

        assertThrows(IncomparableFieldException) {
            service.filter(new ComplexFilter(simpleFilters: [new SimpleFilter(
                    field: "tags", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "a")]))
        }
    }

    @Test
    void testRelationalOperatorOnMapValuedFieldIsRejected() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { structuredData() } as LookupService

        assertThrows(IncomparableFieldException) {
            service.filter(new ComplexFilter(simpleFilters: [new SimpleFilter(
                    field: "meta", operator: FilterConstants.OPERATOR_LESS_THAN, value: "a")]))
        }
    }

    @Test
    void testSortOnListValuedFieldDoesNotThrow() {
        InMemorySortService service = new InMemorySortService()
        service.lookupService = { structuredData() } as LookupService

        def result = service.sort(new ComplexSort(sorts: [new Sort(field: "tags")]))

        assert result.size() == 2
        assert result[0].id == 2
    }

    @Test
    void testSortOnMapValuedFieldDoesNotThrow() {
        InMemorySortService service = new InMemorySortService()
        service.lookupService = { structuredData() } as LookupService

        def result = service.sort(new ComplexSort(sorts: [new Sort(field: "meta")]))

        assert result.size() == 2
        assert result[0].id == 2
    }

    @Test
    void testSortWithMissingFieldDoesNotThrow() {
        InMemorySortService service = new InMemorySortService()
        service.lookupService = { [[id: 1, name: "a"], [id: 2]] } as LookupService

        def result = service.sort(new ComplexSort(sorts: [new Sort(field: "name")]))

        assert result.size() == 2
        assert result[0].id == 2
    }

    @Test
    void testAverageOverMissingFieldReturnsNull() {
        InMemoryAggregationService service = new InMemoryAggregationService()
        service.lookupService = { [[id: 1, age: 10], [id: 2, age: 10]] } as LookupService

        def result = service.aggregate(new Aggregation(groupBy: ["age"],
                functions: [new AggregationFunction(field: "salary", functionName: AggregationConstants.AVERAGE)]))

        assert result.size() == 1
        assert result[0].average_salary == null
    }

    @Test
    void testAverageIgnoresNullValues() {
        InMemoryAggregationService service = new InMemoryAggregationService()
        service.lookupService = { [[id: 1, age: 10, salary: 10], [id: 2, age: 10, salary: null]] } as LookupService

        def result = service.aggregate(new Aggregation(groupBy: ["age"],
                functions: [new AggregationFunction(field: "salary", functionName: AggregationConstants.AVERAGE)]))

        assert result[0].average_salary == 10
    }

    @Test
    void testAggregationDoesNotMutateGroupKeys() {
        def rows = [[id: 1, age: 10], [id: 2, age: 20]]
        InMemoryAggregationService service = new InMemoryAggregationService()
        service.lookupService = { rows } as LookupService

        def result = service.aggregate(new Aggregation(groupBy: ["age"],
                functions: [new AggregationFunction(field: "id", functionName: AggregationConstants.COUNT)]))

        assert result.size() == 2
        assert result.every { it.keySet() == ["age", "count_id"] as Set }
    }
}
