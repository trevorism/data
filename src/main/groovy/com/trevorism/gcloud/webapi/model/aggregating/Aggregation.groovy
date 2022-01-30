package com.trevorism.gcloud.webapi.model.aggregating

import com.trevorism.gcloud.webapi.model.SingleDatasourceRequest

class Aggregation extends SingleDatasourceRequest {

    List<String> groupBy = []
    List<AggregationFunction> functions = []
}
