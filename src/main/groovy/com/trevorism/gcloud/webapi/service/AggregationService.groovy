package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.aggregating.Aggregation

interface AggregationService {

    def aggregate(String kind, Aggregation request)
    def aggregate(long datasetId, Aggregation request)
}
