package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.paging.Limit
import com.trevorism.gcloud.webapi.model.paging.Page

interface PagingService {

    def page(String kind, Page pagingRequest, Limit limitRequest)
    def page(long datasetId, Page pagingRequest, Limit limitRequest)

}