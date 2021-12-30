package com.trevorism.gcloud.webapi.model.filtering

import groovy.transform.ToString

@ToString
class SimpleFilter {
    String id
    String field
    String operator
    String value
}
