package com.trevorism.gcloud.webapi.model.transferring

import com.trevorism.gcloud.webapi.model.MultiDatasourceRequest

class Transfer extends MultiDatasourceRequest {
    String singleRepositoryLookup
    String destinationRepositoryLookup
}
