package com.trevorism.data.service

import com.trevorism.data.model.describing.Describe

interface DescribeService {
    List<String> describe(Describe request)
}