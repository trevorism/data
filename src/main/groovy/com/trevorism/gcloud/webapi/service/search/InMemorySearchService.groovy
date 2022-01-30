package com.trevorism.gcloud.webapi.service.search

import com.trevorism.gcloud.webapi.model.searching.Search
import com.trevorism.gcloud.webapi.service.SearchService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService

class InMemorySearchService implements SearchService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def search(Search search) {
        def dataset = lookupService.lookupDataset(search)
        dataset.findAll { row ->
            boolean truth = false
            row.each { k,v ->
                truth = truth || v.toString().contains(search.query)
            }
            return truth
        }

    }
}
