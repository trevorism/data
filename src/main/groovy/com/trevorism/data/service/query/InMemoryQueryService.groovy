package com.trevorism.data.service.query


import com.trevorism.data.model.Query
import com.trevorism.data.service.QueryService
import com.trevorism.data.service.lookup.DatastoreLookupService
import com.trevorism.data.service.paging.InMemoryPagingService

class InMemoryQueryService implements QueryService{

    private com.trevorism.data.service.AggregationService aggregationService = new com.trevorism.data.service.aggregate.InMemoryAggregationService()
    private com.trevorism.data.service.FilterService filterService = new com.trevorism.data.service.filter.InMemoryFilterService()
    private com.trevorism.data.service.SearchService searchService = new com.trevorism.data.service.search.InMemorySearchService()
    private com.trevorism.data.service.SortService sortService = new com.trevorism.data.service.sort.InMemorySortService()
    private com.trevorism.data.service.PagingService pagingService = new InMemoryPagingService()
    private com.trevorism.data.service.lookup.LookupService lookupService = new DatastoreLookupService()

    @Override
    def query(Query request) {
        def dataset = lookupService.lookupDataset(request)

        aggregationService.lookupService = { dataset } as com.trevorism.data.service.lookup.LookupService
        dataset = aggregationService.aggregate(request.group)

        filterService.lookupService = { dataset } as com.trevorism.data.service.lookup.LookupService
        dataset = filterService.filter(request.where)

        searchService.lookupService = { dataset } as com.trevorism.data.service.lookup.LookupService
        dataset = searchService.search(request.find)

        sortService.lookupService = { dataset } as com.trevorism.data.service.lookup.LookupService
        dataset = sortService.sort(request.order)

        pagingService.lookupService = { dataset } as com.trevorism.data.service.lookup.LookupService
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
