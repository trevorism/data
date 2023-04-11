package com.trevorism.data.service

import com.trevorism.data.model.Query

interface QueryService {
    def query(Query request)
}