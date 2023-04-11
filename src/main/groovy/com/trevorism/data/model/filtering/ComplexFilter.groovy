package com.trevorism.data.model.filtering

import com.trevorism.data.model.SingleDatasourceRequest

class ComplexFilter extends SingleDatasourceRequest {
    String type // simple | and | or   (simple is the default)
    List<SimpleFilter> simpleFilters = []
    List<ComplexFilter> complexFilters = []
}
