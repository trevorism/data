package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.combining.Intersection
import com.trevorism.gcloud.webapi.model.combining.Join
import com.trevorism.gcloud.webapi.model.combining.Union

interface CombineService {

    def join(Join request)
    def intersect(Intersection request)
    def union(Union request)
}