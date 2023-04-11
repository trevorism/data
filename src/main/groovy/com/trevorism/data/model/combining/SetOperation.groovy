package com.trevorism.data.model.combining

import com.trevorism.data.model.MultiDatasourceRequest

class SetOperation extends MultiDatasourceRequest {
    String type
    List<String> datasetUrls = []
}
