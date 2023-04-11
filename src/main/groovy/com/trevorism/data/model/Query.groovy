package com.trevorism.data.model

class Query extends SingleDatasourceRequest {
    List<String> fields
    com.trevorism.data.model.filtering.ComplexFilter where
    com.trevorism.data.model.searching.Search find
    com.trevorism.data.model.sorting.ComplexSort order
    com.trevorism.data.model.paging.Page limit
    com.trevorism.data.model.aggregating.Aggregation group
}
