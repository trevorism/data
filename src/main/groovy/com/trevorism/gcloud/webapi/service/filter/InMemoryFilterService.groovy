package com.trevorism.gcloud.webapi.service.filter

import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.filtering.FilterConstants
import com.trevorism.gcloud.webapi.model.filtering.SimpleFilter
import com.trevorism.gcloud.webapi.service.FilterService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService

class InMemoryFilterService implements FilterService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def filter(ComplexFilter request) {
        def dataset = lookupService.lookupDataset(request)

        dataset.findAll { row ->
            if((request.type != FilterConstants.AND || request.type != FilterConstants.OR) && request.simpleFilters){
                SimpleFilter filter = request.simpleFilters[0]
                applySimpleFilter(row, filter)


            }


        }
    }

    private boolean applySimpleFilter(Map<String,Object> row, SimpleFilter filter) {

        switch (filter.operator) {
        //case "=": return row[filter.field.toLowerCase()] == filter.value
        }
    }
}
