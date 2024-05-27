package com.trevorism.data.service.describe

import com.trevorism.data.model.describing.Describe
import com.trevorism.data.model.describing.DescribeConstants
import com.trevorism.data.service.DescribeService

class InMemoryDescribeService implements DescribeService {

    @Override
    List<String> describe(Describe request) {
        [DescribeConstants.LIST, DescribeConstants.CREATE, DescribeConstants.READ,
         DescribeConstants.UPDATE, DescribeConstants.DELETE, DescribeConstants.SEARCH,
         DescribeConstants.FILTER, DescribeConstants.PAGE, DescribeConstants.SORT,
         DescribeConstants.AGGREGATE]
    }
}
