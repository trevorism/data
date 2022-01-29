package com.trevorism.gcloud.webapi.service.filter

import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.filtering.FilterConstants
import com.trevorism.gcloud.webapi.model.filtering.SimpleFilter
import com.trevorism.gcloud.webapi.service.FilterService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService

import java.time.LocalDateTime
import java.time.ZoneOffset

class InMemoryFilterService implements FilterService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def filter(ComplexFilter request) {
        def dataset = lookupService.lookupDataset(request)

        dataset.findAll { row ->
            return applyComplexFilter(row, request)
        }
    }

    private boolean applyComplexFilter(row, ComplexFilter request) {
        if(request.type == FilterConstants.AND && request.complexFilters){
            boolean truth = true
            request.complexFilters.each {
                truth = truth && applyComplexFilter(row, it)
            }
            return truth
        }

        if(request.type == FilterConstants.AND && request.simpleFilters){
            boolean truth = true
            request.simpleFilters.each {
                truth = truth && applySimpleFilter(row, it)
            }
            return truth
        }


        if(request.type == FilterConstants.OR && request.complexFilters){
            boolean truth = false
            request.complexFilters.each {
                truth = truth || applyComplexFilter(row, it)
            }
            return truth
        }

        if(request.type == FilterConstants.OR && request.simpleFilters){
            boolean truth = false
            request.simpleFilters.each {
                truth = truth || applySimpleFilter(row, it)
            }
            return truth
        }

        if (request.simpleFilters) {
            return applySimpleFilter(row, request.simpleFilters[0])
        }

        return false
    }

    private boolean applySimpleFilter(Map<String,Object> row, SimpleFilter filter) {
        def value = filter.value
        if(filter.type?.toLowerCase() == FilterConstants.TYPE_DATE)
            value = Date.from(LocalDateTime.parse(value).toInstant(ZoneOffset.UTC))
        else if(filter.type?.toLowerCase() == FilterConstants.TYPE_NUMBER)
            value = Double.valueOf(value)

        switch (filter.operator) {
            case FilterConstants.OPERATOR_EQUAL: return row[filter.field.toLowerCase()] == value
            case FilterConstants.OPERATOR_GREATER_THAN: return row[filter.field.toLowerCase()] > value
            case FilterConstants.OPERATOR_GREATER_THAN_OR_EQUAL: return row[filter.field.toLowerCase()] >= value
            case FilterConstants.OPERATOR_LESS_THAN: return row[filter.field.toLowerCase()] < value
            case FilterConstants.OPERATOR_LESS_THAN_OR_EQUAL: return row[filter.field.toLowerCase()] <= value
            case FilterConstants.OPERATOR_NOT_EQUAL: return row[filter.field.toLowerCase()] != value
        }
    }
}
