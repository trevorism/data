package com.trevorism.gcloud.webapi.model.aggregating

class Aggregation {
    String id
    List<String> groupBy
    List<AggregationFunction> functions
}
