package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.sorting.ComplexSort

interface SortService {

    def sort(Long datasetId, ComplexSort request)
}
