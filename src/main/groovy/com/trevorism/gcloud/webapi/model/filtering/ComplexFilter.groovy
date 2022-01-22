package com.trevorism.gcloud.webapi.model.filtering

import com.trevorism.gcloud.webapi.model.SingleDatasourceRequest

class ComplexFilter extends SingleDatasourceRequest {
    String type
    List<SimpleFilter> simpleFilters
    List<ComplexFilter> complexFilters
}
