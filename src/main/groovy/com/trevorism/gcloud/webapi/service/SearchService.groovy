package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.searching.Search

interface SearchService {

    def search(Search search)
}