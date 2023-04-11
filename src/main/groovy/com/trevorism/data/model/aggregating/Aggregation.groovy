package com.trevorism.data.model.aggregating

import com.trevorism.data.model.SingleDatasourceRequest

class Aggregation extends SingleDatasourceRequest {

    List<String> groupBy = []
    List<AggregationFunction> functions = []
}
