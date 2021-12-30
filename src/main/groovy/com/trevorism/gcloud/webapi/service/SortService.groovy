package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.sorting.ComplexSort

interface SortService {

    def sort(String kind, ComplexSort request)
    def sort(long datasetId, ComplexSort request)
}
