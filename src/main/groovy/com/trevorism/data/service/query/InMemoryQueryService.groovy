package com.trevorism.data.service.query


import com.trevorism.data.model.Query
import com.trevorism.data.service.AggregationService
import com.trevorism.data.service.FilterService
import com.trevorism.data.service.PagingService
import com.trevorism.data.service.QueryService
import com.trevorism.data.service.SearchService
import com.trevorism.data.service.SortService
import com.trevorism.data.service.aggregate.InMemoryAggregationService
import com.trevorism.data.service.filter.InMemoryFilterService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.lookup.LookupService
import com.trevorism.data.service.paging.InMemoryPagingService
import com.trevorism.data.service.search.InMemorySearchService
import com.trevorism.data.service.sort.InMemorySortService
import com.trevorism.https.SecureHttpClient

class InMemoryQueryService implements QueryService{

    private AggregationService aggregationService
    private FilterService filterService
    private SearchService searchService
    private SortService sortService
    private PagingService pagingService
    private LookupService lookupService

    InMemoryQueryService(SecureHttpClient passThruSecureHttpClient){
        aggregationService = new InMemoryAggregationService(passThruSecureHttpClient)
        filterService = new InMemoryFilterService(passThruSecureHttpClient)
        searchService = new InMemorySearchService(passThruSecureHttpClient)
        sortService = new InMemorySortService(passThruSecureHttpClient)
        pagingService = new InMemoryPagingService(passThruSecureHttpClient)
        lookupService = new DatastoreLookupService(passThruSecureHttpClient)
    }

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
