package com.trevorism.data.service.sort


import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.service.SortService
import com.trevorism.data.service.lookup.DatastoreLookupService

class InMemorySortService implements SortService{

    private com.trevorism.data.service.lookup.LookupService lookupService = new DatastoreLookupService()

    @Override
    def sort(ComplexSort request) {
        def dataset = lookupService.lookupDataset(request)

        if(!request || !request.sorts)
            return dataset

        request.sorts.reverse().each {simpleSort ->
            if(simpleSort.descending)
                dataset = dataset.sort{x1, x2 -> x2[simpleSort.field?.toLowerCase()] <=> x1[simpleSort.field?.toLowerCase()]}
            else
                dataset = dataset.sort{x1, x2 -> x1[simpleSort.field?.toLowerCase()] <=> x2[simpleSort.field?.toLowerCase()]}
        }

        return dataset
    }
}
