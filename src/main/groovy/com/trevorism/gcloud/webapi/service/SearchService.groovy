package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.searching.Search

interface SearchService {

    def search(String kind, Search search)
    def search(long datasetId, Search search)
}