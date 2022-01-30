package com.trevorism.gcloud.webapi.service.sort

import com.trevorism.gcloud.webapi.model.sorting.ComplexSort
import com.trevorism.gcloud.webapi.service.SortService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService

class InMemorySortService implements SortService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def sort(ComplexSort request) {
        def dataset = lookupService.lookupDataset(request)

        request.sorts?.reverse().each {simpleSort ->
            if(simpleSort.descending)
                dataset = dataset.sort{x1, x2 -> x2[simpleSort.field?.toLowerCase()] <=> x1[simpleSort.field?.toLowerCase()]}
            else
                dataset = dataset.sort{x1, x2 -> x1[simpleSort.field?.toLowerCase()] <=> x2[simpleSort.field?.toLowerCase()]}
        }

        return dataset
    }
}
