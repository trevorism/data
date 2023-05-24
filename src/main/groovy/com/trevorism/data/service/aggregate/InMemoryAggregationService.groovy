package com.trevorism.data.service.aggregate

import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.aggregating.AggregationConstants
import com.trevorism.data.model.aggregating.AggregationFunction
import com.trevorism.data.service.AggregationService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.lookup.LookupService
import com.trevorism.https.SecureHttpClient

class InMemoryAggregationService implements AggregationService{

    private LookupService lookupService

    InMemoryAggregationService(SecureHttpClient httpClient)  {
        this.lookupService = new DatastoreLookupService(httpClient)
    }

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

    private static def computeAggregationValue(AggregationFunction it, List<Map<String, Object>> v) {
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
