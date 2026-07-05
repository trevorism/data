package com.trevorism.gcloud.webapi.service.describe

import com.trevorism.data.model.describing.Describe
import com.trevorism.data.model.describing.DescribeConstants
import com.trevorism.data.service.describe.InMemoryDescribeService
import org.junit.jupiter.api.Test

class DescribeServiceTest {

    private InMemoryDescribeService service = new InMemoryDescribeService()

    @Test
    void testDescribeReturnsSupportedOperations() {
        List<String> result = service.describe(new Describe())

        assert result.size() == 10
        assert result.contains(DescribeConstants.LIST)
        assert result.contains(DescribeConstants.CREATE)
        assert result.contains(DescribeConstants.READ)
        assert result.contains(DescribeConstants.UPDATE)
        assert result.contains(DescribeConstants.DELETE)
        assert result.contains(DescribeConstants.SEARCH)
        assert result.contains(DescribeConstants.FILTER)
        assert result.contains(DescribeConstants.PAGE)
        assert result.contains(DescribeConstants.SORT)
        assert result.contains(DescribeConstants.AGGREGATE)
    }

    @Test
    void testDescribeIsStableForNullRequest() {
        assert service.describe(null) == service.describe(new Describe())
    }
}
