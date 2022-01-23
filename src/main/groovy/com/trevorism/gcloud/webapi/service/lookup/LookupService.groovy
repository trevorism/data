package com.trevorism.gcloud.webapi.service.lookup

import com.trevorism.gcloud.webapi.model.SingleDatasourceRequest

interface LookupService {

    List<Map<String, Object>> lookupDataset(SingleDatasourceRequest request)
}