package com.trevorism.data.service

import com.trevorism.data.model.aggregating.Aggregation

interface AggregationService {

    def aggregate(Aggregation request)
}
