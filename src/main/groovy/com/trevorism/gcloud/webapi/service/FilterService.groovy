package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter

interface FilterService {

    def filter(String kind, ComplexFilter request)
    def filter(long dataSetId, ComplexFilter request)

}