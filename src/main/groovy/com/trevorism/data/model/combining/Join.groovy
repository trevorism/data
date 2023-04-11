package com.trevorism.data.model.combining

import com.trevorism.data.model.MultiDatasourceRequest

class Join extends MultiDatasourceRequest {
    List<JoinColumns> joinFields = []
}
