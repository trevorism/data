package com.trevorism.data.service


import com.trevorism.data.model.combining.Join
import com.trevorism.data.model.combining.SetOperation

interface CombineService {

    def join(Join request)
    def combine(SetOperation setOperation)
}