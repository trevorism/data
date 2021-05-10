package com.trevorism.gcloud.webapi.model

import groovy.transform.ToString

@ToString
class Filter {
    String id
    String name
    String field
    String operator
    String value
}
