package com.trevorism.data.service.sort


import com.trevorism.data.DataFields
import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.service.SortService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.lookup.LookupService
import com.trevorism.https.SecureHttpClient

class InMemorySortService implements SortService{

    private LookupService lookupService

    InMemorySortService(SecureHttpClient passThruSecureHttpClient){
        lookupService = new DatastoreLookupService(passThruSecureHttpClient)
    }

    @Override
    def sort(ComplexSort request) {
        def dataset = lookupService.lookupDataset(request)

        if(!request || !request.sorts)
            return dataset

        request.sorts.reverse().each {simpleSort ->
            if(simpleSort.descending)
                dataset = dataset.sort{x1, x2 -> DataFields.compare(DataFields.valueOf(x2, simpleSort.field), DataFields.valueOf(x1, simpleSort.field))}
            else
                dataset = dataset.sort{x1, x2 -> DataFields.compare(DataFields.valueOf(x1, simpleSort.field), DataFields.valueOf(x2, simpleSort.field))}
        }

        return dataset
    }
}
