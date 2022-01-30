package com.trevorism.gcloud.webapi.service.describe

import com.trevorism.gcloud.webapi.model.describing.Describe
import com.trevorism.gcloud.webapi.model.describing.DescribeConstants
import com.trevorism.gcloud.webapi.service.DescribeService

class InMemoryDescribeService implements DescribeService{

    @Override
    def describe(Describe request) {
        [DescribeConstants.LIST, DescribeConstants.CREATE, DescribeConstants.READ,
         DescribeConstants.UPDATE, DescribeConstants.DELETE, DescribeConstants.SEARCH,
         DescribeConstants.FILTER, DescribeConstants.PAGE, DescribeConstants.SORT]
    }
}
