package com.trevorism.gcloud.webapi.model.combining

import com.trevorism.gcloud.webapi.model.MultiDatasourceRequest

class Join extends MultiDatasourceRequest {
    List<JoinColumns> joinFields = []
}
