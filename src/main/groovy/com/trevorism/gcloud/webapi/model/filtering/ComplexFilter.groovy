package com.trevorism.gcloud.webapi.model.filtering

class ComplexFilter {
    String id
    String type
    List<SimpleFilter> simpleFilters
    List<ComplexFilter> complexFilters
}
