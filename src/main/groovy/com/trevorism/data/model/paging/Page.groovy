package com.trevorism.data.model.paging

import com.trevorism.data.model.SingleDatasourceRequest

class Page extends SingleDatasourceRequest{
    int page
    int pageSize
    int limit
}
