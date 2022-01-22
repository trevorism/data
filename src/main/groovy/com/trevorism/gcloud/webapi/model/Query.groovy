package com.trevorism.gcloud.webapi.model

import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.paging.Page
import com.trevorism.gcloud.webapi.model.searching.Search
import com.trevorism.gcloud.webapi.model.sorting.ComplexSort

class Query extends SingleDatasourceRequest {
    List<String> fields
    ComplexFilter where
    Search find
    ComplexSort order
    Page limit
    Aggregation group
}
