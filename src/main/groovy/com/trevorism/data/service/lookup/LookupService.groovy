package com.trevorism.data.service.lookup

import com.trevorism.data.model.SingleDatasourceRequest

interface LookupService {

    List<Map<String, Object>> lookupDataset(SingleDatasourceRequest request)
}