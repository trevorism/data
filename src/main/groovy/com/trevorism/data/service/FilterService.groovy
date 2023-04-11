package com.trevorism.data.service

import com.trevorism.data.model.filtering.ComplexFilter

interface FilterService {

    def filter(ComplexFilter request)

}