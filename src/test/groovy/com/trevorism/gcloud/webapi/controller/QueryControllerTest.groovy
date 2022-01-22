package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.Query
import com.trevorism.gcloud.webapi.model.searching.Search
import com.trevorism.gcloud.webapi.model.sorting.ComplexSort
import com.trevorism.gcloud.webapi.model.sorting.Sort
import org.junit.Test

class QueryControllerTest {

    @Test
    void testOperate() {
        QueryController resultController = new QueryController()
        Query query = new Query(lookup: new Search(query: "domainmodel"), order: new ComplexSort(sorts: [new Sort(field: "classname", descending: false)]))
        def list = resultController.operate(query)
        list.each {
            println it
        }
    }
}
