package com.trevorism.data.service.request

import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.combining.Join
import com.trevorism.data.model.combining.SetOperation
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.model.paging.Page
import com.trevorism.data.model.searching.Search
import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.model.transferring.Transfer

class OperationRequestFactory {

    def buildRequestObject(Map<String, Object> request) {

        try { return (Aggregation)request }
        catch (Exception ignored) { }

        try { return (ComplexFilter)request}
        catch (Exception ignored) { }

        try { return (Page)request}
        catch (Exception ignored) { }

        try { return (Search)request}
        catch (Exception ignored) { }

        try { return (ComplexSort)request}
        catch (Exception ignored) { }

        try { return (Transfer)request}
        catch (Exception ignored) { }

        try { return (SetOperation)request}
        catch (Exception ignored) { }

        try { return (Join)request}
        catch (Exception ignored) { }

        throw new RuntimeException("Unable to parse request into known data operation")
    }

}
