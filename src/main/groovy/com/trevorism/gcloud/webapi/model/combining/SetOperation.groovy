package com.trevorism.gcloud.webapi.model.combining

import com.trevorism.gcloud.webapi.model.MultiDatasourceRequest

class SetOperation extends MultiDatasourceRequest {
    String type
    List<String> datasetUrls = []
}
