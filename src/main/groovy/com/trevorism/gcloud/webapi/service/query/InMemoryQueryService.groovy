package com.trevorism.gcloud.webapi.service.query

import com.trevorism.gcloud.webapi.model.Query
import com.trevorism.gcloud.webapi.service.AggregationService
import com.trevorism.gcloud.webapi.service.FilterService
import com.trevorism.gcloud.webapi.service.PagingService
import com.trevorism.gcloud.webapi.service.QueryService
import com.trevorism.gcloud.webapi.service.SearchService
import com.trevorism.gcloud.webapi.service.SortService
import com.trevorism.gcloud.webapi.service.aggregate.InMemoryAggregationService
import com.trevorism.gcloud.webapi.service.filter.InMemoryFilterService
import com.trevorism.gcloud.webapi.service.lookup.DatastoreLookupService
import com.trevorism.gcloud.webapi.service.lookup.LookupService
import com.trevorism.gcloud.webapi.service.paging.InMemoryPagingService
import com.trevorism.gcloud.webapi.service.search.InMemorySearchService
import com.trevorism.gcloud.webapi.service.sort.InMemorySortService

class InMemoryQueryService implements QueryService{

    private AggregationService aggregationService = new InMemoryAggregationService()
    private FilterService filterService = new InMemoryFilterService()
    private SearchService searchService = new InMemorySearchService()
    private SortService sortService = new InMemorySortService()
    private PagingService pagingService = new InMemoryPagingService()
    private LookupService lookupService = new DatastoreLookupService()

    @Override
    def query(Query request) {
        def dataset = lookupService.lookupDataset(request)

        aggregationService.lookupService = { dataset } as LookupService
        dataset = aggregationService.aggregate(request.group)

        filterService.lookupService = { dataset } as LookupService
        dataset = filterService.filter(request.where)

        searchService.lookupService = { dataset } as LookupService
        dataset = searchService.search(request.find)

        sortService.lookupService = { dataset } as LookupService
        dataset = sortService.sort(request.order)

        pagingService.lookupService = { dataset } as LookupService
        dataset = pagingService.page(request.limit)

        dataset = handleSelectFields(request, dataset)

        return dataset
    }

    private List<LinkedHashMap<Object, Object>> handleSelectFields(Query request, List dataset) {
        if (request.fields) {
            dataset = dataset.collect { obj ->
                def map = [:]
                request.fields.each {
                    map.put(it, obj[it])
                }
                return map
            }
        }
        dataset
    }
}
