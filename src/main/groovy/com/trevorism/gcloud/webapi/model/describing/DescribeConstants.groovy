package com.trevorism.gcloud.webapi.model.describing

import jdk.nashorn.internal.scripts.JO

import javax.ws.rs.DELETE

class DescribeConstants {
    static final String AGGREGATE = "aggregate"
    static final String LIST = "list"
    static final String CREATE = "create"
    static final String READ = "read"
    static final String UPDATE = "update"
    static final String FILTER = "filter"
    static final String PAGE = "page"
    static final String SEARCH = "search"
    static final String SORT = "sort"
    static final String TRANSFER = "transfer"
    static final String UNION = "union"
    static final String INTERSECT = "intersect"
    static final String JOIN = "join"

    static final def ALL_FUNCTIONS = [LIST, READ, CREATE, DELETE, UPDATE, SEARCH, FILTER, SORT, TRANSFER, PAGE, AGGREGATE, UNION, INTERSECT, JOIN]

}
