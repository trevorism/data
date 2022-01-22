package com.trevorism.gcloud.webapi.service


import com.trevorism.gcloud.webapi.model.combining.Join
import com.trevorism.gcloud.webapi.model.combining.SetOperation

interface CombineService {

    def join(Join request)
    def combine(SetOperation setOperation)
}