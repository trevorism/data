package com.trevorism.data.service.request

class OperationRequestFactory {

    def buildRequestObject(Map<String, Object> request) {

        try { return (com.trevorism.data.model.aggregating.Aggregation)request }
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.filtering.ComplexFilter)request}
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.paging.Page)request}
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.searching.Search)request}
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.sorting.ComplexSort)request}
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.transferring.Transfer)request}
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.combining.SetOperation)request}
        catch (Exception ignored) { }

        try { return (com.trevorism.data.model.combining.Join)request}
        catch (Exception ignored) { }

        throw new RuntimeException("Unable to parse request into known data operation")
    }

}
