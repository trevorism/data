package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.Query

interface QueryService {
    def query(Query request)
}