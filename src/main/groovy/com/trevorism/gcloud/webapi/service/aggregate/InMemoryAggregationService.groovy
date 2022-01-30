package com.trevorism.gcloud.webapi.service.aggregate

import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.service.AggregationService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService

class InMemoryAggregationService implements AggregationService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def aggregate(Aggregation request) {
        //This is hard :(
        def dataset = lookupService.lookupDataset(request)
    }
}
