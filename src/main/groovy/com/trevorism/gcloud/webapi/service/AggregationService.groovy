package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.aggregating.Aggregation

interface AggregationService {

    def aggregate(Long datasetId, Aggregation request)
}
