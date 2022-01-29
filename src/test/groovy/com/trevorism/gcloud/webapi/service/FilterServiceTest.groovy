package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.filtering.FilterConstants
import com.trevorism.gcloud.webapi.model.filtering.SimpleFilter
import com.trevorism.gcloud.webapi.service.filter.InMemoryFilterService
import com.trevorism.gcloud.webapi.service.lookup.LookupService
import org.junit.Test

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FilterServiceTest {

    @Test
    void testStringFiltering() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(
                simpleFilters: [new SimpleFilter(type: FilterConstants.TYPE_STRING, field: "name", operator: FilterConstants.OPERATOR_EQUAL, value: "Trev")]))

        assert result.size() == 1
        assert result[0].id == 3
    }

    @Test
    void testStringFilteringMissing() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(type: FilterConstants.TYPE_STRING,
                simpleFilters: [new SimpleFilter(field: "name", operator: FilterConstants.OPERATOR_EQUAL, value: "trev")]))

        assert !result
    }

    @Test
    void testAgeFiltering() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(
                simpleFilters: [new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_LESS_THAN_OR_EQUAL, value: "10")]))

        assert result.size() == 2
        assert result[0].id == 1
        assert result[1].id == 2
    }

    @Test
    void testAgeFilteringMissing() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(type: FilterConstants.TYPE_NUMBER,
                simpleFilters: [new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "22")]))

        assert !result
    }

    @Test
    void testDateFiltering() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC)
        def dateString = formatter.format(Instant.now())

        def result = service.filter(new ComplexFilter(
                simpleFilters: [new SimpleFilter(type: FilterConstants.TYPE_DATE, field: "birthday", operator: FilterConstants.OPERATOR_LESS_THAN, value: dateString)]))

        assert result.size() == 3
        assert result[0].id == 1
        assert result[1].id == 3
        assert result[2].id == 4
    }

    @Test
    void testDateFilteringMissing() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC)
        def dateString = formatter.format(Instant.now().minusMillis(-5000))

        def result = service.filter(new ComplexFilter(
                simpleFilters: [new SimpleFilter(type: FilterConstants.TYPE_DATE, field: "birthday", operator: FilterConstants.OPERATOR_GREATER_THAN, value: dateString)]))

        assert !result
    }

    @Test
    void testAndFiltering() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(type: FilterConstants.AND,
                simpleFilters: [
                        new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "9"),
                        new SimpleFilter(field: "name", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "R"),
                ]))

        assert result.size() == 2
        assert result[0].id == 1
        assert result[1].id == 3
    }

    @Test
    void testOrFiltering() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(type: FilterConstants.OR,
                simpleFilters: [
                        new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "20"),
                        new SimpleFilter(field: "name", operator: FilterConstants.OPERATOR_LESS_THAN, value: "Ro"),
                ]))

        assert result.size() == 2
        assert result[0].id == 1
        assert result[1].id == 4
    }

    @Test
    void testComplexFiltering() {
        InMemoryFilterService service = new InMemoryFilterService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.filter(new ComplexFilter(type: FilterConstants.OR,
                simpleFilters: [
                        new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "20"),
                ],
                complexFilters: [
                        new ComplexFilter(type: FilterConstants.AND, simpleFilters: [
                                new SimpleFilter(type: FilterConstants.TYPE_NUMBER, field: "age", operator: FilterConstants.OPERATOR_LESS_THAN_OR_EQUAL, value: "10"),
                                new SimpleFilter(field: "name", operator: FilterConstants.OPERATOR_GREATER_THAN, value: "Ro"),
                        ])
                ]
        ))

        assert result.size() == 2
        assert result[0].id == 2
        assert result[1].id == 4
    }


    private List<Map> createFakeData() {
        def list = []
        list << [id: 1, age: 10, name: "Ren", birthday: Date.from(Instant.now().minus(1, ChronoUnit.DAYS))]
        list << [id: 2, age: 7, name: "Stimpy", birthday: Date.from(Instant.now().minus(0, ChronoUnit.DAYS))]
        list << [id: 3, age: 20, name: "Trev", birthday: Date.from(Instant.now().minus(20, ChronoUnit.DAYS))]
        list << [id: 4, age: 22, name: "Ism", birthday: Date.from(Instant.now().minus(22, ChronoUnit.DAYS))]
        return list
    }
}
