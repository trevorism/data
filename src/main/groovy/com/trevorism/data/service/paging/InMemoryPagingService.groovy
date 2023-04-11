package com.trevorism.data.service.paging

import com.trevorism.data.model.paging.Page
import com.trevorism.data.service.PagingService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.lookup.LookupService

class InMemoryPagingService implements PagingService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def page(Page pagingRequest) {
        def dataset = lookupService.lookupDataset(pagingRequest)

        if(!pagingRequest)
            return dataset

        if(pagingRequest.page == 0 || pagingRequest.pageSize == 0){
            if(pagingRequest.limit == 0)
                return dataset
            else
                return dataset.take(pagingRequest.limit)
        }

        int firstIndex = (pagingRequest.page-1) * pagingRequest.pageSize
        if(firstIndex < 0)
            firstIndex = 0
        if(firstIndex > dataset.size()-1)
            return []

        int lastIndex = firstIndex + pagingRequest.pageSize - 1
        if(lastIndex < 1)
            return []
        if(lastIndex > dataset.size()-1){
            lastIndex = dataset.size()-1
        }

        return dataset[firstIndex..lastIndex]

    }
}
