package com.trevorism.gcloud.webapi.service.aggregate

import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.model.aggregating.AggregationConstants
import com.trevorism.gcloud.webapi.model.aggregating.AggregationFunction
import com.trevorism.gcloud.webapi.service.AggregationService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService

class InMemoryAggregationService implements AggregationService{

    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def aggregate(Aggregation request) {
        def dataset = lookupService.lookupDataset(request)

        if(!request?.groupBy)
            return dataset

        def groupMap = dataset.groupBy {obj ->
            def map = [:]
            request.groupBy.each {
                map.put(it, obj[it])
            }
            return map
        }

        groupMap.collect { k,v ->
            def map = k
            request.functions.each {
                def value = computeAggregationValue(it, v)
                map.put("${it.functionName}_${it.field}".toString(), value)
            }
            return map
        }

    }

    private def computeAggregationValue(AggregationFunction it, List<Map<String, Object>> v) {
        switch (it.functionName) {
            case AggregationConstants.FIRST: return v.first()[it.field]
            case AggregationConstants.COUNT: return v.size()
            case AggregationConstants.MAX: return v[it.field].max()
            case AggregationConstants.MIN: return v[it.field].min()
            case AggregationConstants.SUM: return v[it.field].sum()
            case AggregationConstants.AVERAGE: return ((double)v[it.field].sum())/((double)v.size())
        }
    }
}
