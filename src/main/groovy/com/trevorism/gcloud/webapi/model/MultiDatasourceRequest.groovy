package com.trevorism.gcloud.webapi.model

abstract class MultiDatasourceRequest {
    String id
    List<String> lookups = []
}
