package com.trevorism.gcloud.webapi.model.paging

import com.trevorism.gcloud.webapi.model.SingleDatasourceRequest

class Page extends SingleDatasourceRequest{
    int page
    int pageSize
    int limit
}
