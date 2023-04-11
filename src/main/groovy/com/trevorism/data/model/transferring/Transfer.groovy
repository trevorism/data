package com.trevorism.data.model.transferring

import com.trevorism.data.model.MultiDatasourceRequest

class Transfer extends MultiDatasourceRequest {
    String singleRepositoryLookup
    String destinationRepositoryLookup
}
