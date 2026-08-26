package com.trevorism.data.service.aggregate

import com.trevorism.data.DataFields
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
                map.put(it, DataFields.valueOf(obj, it))
            }
            return map
        }

        groupMap.collect { k,v ->
            def map = new LinkedHashMap(k)
            request.functions.each {
                def value = computeAggregationValue(it, v)
                map.put("${it.functionName}_${it.field}".toString(), value)
            }
            return map
        }

    }

    private static def computeAggregationValue(AggregationFunction function, List<Map<String, Object>> rows) {
        List values = rows.collect { DataFields.valueOf(it, function.field) }
        List present = values.findAll { it != null }

        switch (function.functionName) {
            case AggregationConstants.FIRST: return values.first()
            case AggregationConstants.COUNT: return rows.size()
            case AggregationConstants.MAX: return present ? present.max { a, b -> DataFields.compare(a, b) } : null
            case AggregationConstants.MIN: return present ? present.min { a, b -> DataFields.compare(a, b) } : null
            case AggregationConstants.SUM: return present ? present.sum() : null
            case AggregationConstants.AVERAGE: return present ? ((double) present.sum()) / ((double) present.size()) : null
        }
    }
}
