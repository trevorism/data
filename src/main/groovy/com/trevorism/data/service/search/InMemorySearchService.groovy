package com.trevorism.data.service.search

import com.trevorism.data.model.searching.Search
import com.trevorism.data.service.SearchService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.lookup.LookupService

class InMemorySearchService implements SearchService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def search(Search search) {
        def dataset = lookupService.lookupDataset(search)
        if(!search?.query)
            return dataset

        dataset.findAll { row ->
            boolean truth = false
            row.each { k,v ->
                truth = truth || v.toString().contains(search.query)
            }
            return truth
        }

    }
}
