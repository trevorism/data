package com.trevorism.data.model

import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.model.paging.Page
import com.trevorism.data.model.searching.Search
import com.trevorism.data.model.sorting.ComplexSort

class Query extends SingleDatasourceRequest {
    List<String> fields
    ComplexFilter where
    Search find
    ComplexSort order
    Page limit
    Aggregation group
}
