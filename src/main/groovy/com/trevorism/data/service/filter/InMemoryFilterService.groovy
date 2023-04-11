package com.trevorism.data.service.filter

import com.trevorism.data.model.exception.UnparseableDateException
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.model.filtering.FilterConstants
import com.trevorism.data.model.filtering.SimpleFilter
import com.trevorism.data.service.FilterService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.lookup.LookupService

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

class InMemoryFilterService implements FilterService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def filter(ComplexFilter request) {
        def dataset = lookupService.lookupDataset(request)

        if(!request?.simpleFilters && !request?.complexFilters)
            return dataset

        dataset.findAll { row ->
            return applyComplexFilter(row, request)
        }
    }

    private boolean applyComplexFilter(row, ComplexFilter request) {
        if(request.type == FilterConstants.AND){
            return handleConjunction(request, row)
        }

        if(request.type == FilterConstants.OR){
            return handleDisjunction(request, row)
        }

        if (request.simpleFilters) {
            return applySimpleFilter(row, request.simpleFilters[0])
        }

        return false
    }

    private boolean handleDisjunction(ComplexFilter request, row) {
        boolean truth = false
        request.complexFilters.each {
            truth = truth || applyComplexFilter(row, it)
        }
        request.simpleFilters.each {
            truth = truth || applySimpleFilter(row, it)
        }
        return truth
    }

    private boolean handleConjunction(ComplexFilter request, row) {
        boolean truth = true
        request.complexFilters.each {
            truth = truth && applyComplexFilter(row, it)
        }
        request.simpleFilters.each {
            truth = truth && applySimpleFilter(row, it)
        }
        return truth
    }

    private boolean applySimpleFilter(Map<String,Object> row, SimpleFilter filter) {
        def value = filter.value
        def objField = row[filter.field.toLowerCase()]
        if(filter.type?.toLowerCase() == FilterConstants.TYPE_DATE){
            value = parseDateValue(value)
            if(objField instanceof String){
                objField = parseDateValue(objField)
            }
        }
        else if(filter.type?.toLowerCase() == FilterConstants.TYPE_NUMBER) {
            value = Double.valueOf(value)
            if(objField instanceof String){
                objField = Double.valueOf(objField)
            }
        }
        switch (filter.operator) {
            case FilterConstants.OPERATOR_EQUAL: return objField == value
            case FilterConstants.OPERATOR_GREATER_THAN: return objField > value
            case FilterConstants.OPERATOR_GREATER_THAN_OR_EQUAL: return objField >= value
            case FilterConstants.OPERATOR_LESS_THAN: return objField < value
            case FilterConstants.OPERATOR_LESS_THAN_OR_EQUAL: return objField <= value
            case FilterConstants.OPERATOR_NOT_EQUAL: return objField != value
        }
    }

    private Date parseDateValue(String value) {
        try{
            return Date.from(LocalDateTime.parse(value).toInstant(ZoneOffset.UTC))
        }catch(ignored){}
        try{
            return Date.from(ZonedDateTime.parse(value).toInstant())
        }catch(ignored){}

        throw new UnparseableDateException("Unparseable date: ${value}")
    }
}
