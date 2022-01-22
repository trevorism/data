package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.describing.Describe

interface DescribeService {
    def describe(Describe request)
}