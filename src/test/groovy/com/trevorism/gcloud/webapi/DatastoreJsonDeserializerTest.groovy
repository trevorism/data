package com.trevorism.gcloud.webapi

import com.trevorism.data.DataUtils
import com.trevorism.data.DatastoreJsonDeserializer
import com.trevorism.data.model.Query
import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.model.paging.Page
import com.trevorism.data.model.searching.Search
import com.trevorism.data.model.sorting.ComplexSort
import org.junit.jupiter.api.Test

class DatastoreJsonDeserializerTest {

    private DatastoreJsonDeserializer deserializer = new DatastoreJsonDeserializer(DataUtils.gson)

    private static final String STORED_QUERY = '''
        {"id":5,"lookup":"datastore:person",
         "fields":"[\\"name\\",\\"age\\"]",
         "where":"{\\"type\\":\\"and\\",\\"simpleFilters\\":[{\\"type\\":\\"number\\",\\"field\\":\\"age\\",\\"operator\\":\\">\\",\\"value\\":\\"10\\"}],\\"complexFilters\\":[]}",
         "order":"{\\"sorts\\":[{\\"field\\":\\"name\\",\\"descending\\":true}]}",
         "limit":"{\\"page\\":1,\\"pageSize\\":25,\\"limit\\":0}",
         "group":"{\\"groupBy\\":[\\"age\\"],\\"functions\\":[{\\"functionName\\":\\"count\\",\\"field\\":\\"id\\"}]}"}
    '''

    @Test
    void testSavedQueryStoredByDatastore() {
        Query query = deserializer.deserialize(STORED_QUERY, Query)

        assert query.id == "5"
        assert query.lookup == "datastore:person"
        assert query.fields == ["name", "age"]
        assert query.where.type == "and"
        assert query.where.simpleFilters.size() == 1
        assert query.where.simpleFilters[0].field == "age"
        assert query.where.simpleFilters[0].operator == ">"
        assert query.order.sorts.size() == 1
        assert query.order.sorts[0].field == "name"
        assert query.order.sorts[0].descending
        assert query.limit.page == 1
        assert query.limit.pageSize == 25
        assert query.group.groupBy == ["age"]
        assert query.group.functions[0].functionName == "count"
    }

    @Test
    void testSavedPageLowercasedByDatastore() {
        Page page = deserializer.deserialize('{"id":5,"page":2,"pagesize":25,"limit":0,"lookup":"datastore:person"}', Page)

        assert page.page == 2
        assert page.pageSize == 25
        assert page.limit == 0
    }

    @Test
    void testSavedFilterStoredByDatastore() {
        ComplexFilter filter = deserializer.deserialize(
                '{"id":5,"lookup":"datastore:person","type":"or","simplefilters":"[{\\"field\\":\\"name\\",\\"operator\\":\\"=\\",\\"value\\":\\"Trev\\"}]","complexfilters":"[]"}',
                ComplexFilter)

        assert filter.type == "or"
        assert filter.simpleFilters.size() == 1
        assert filter.simpleFilters[0].value == "Trev"
        assert filter.complexFilters.isEmpty()
    }

    @Test
    void testSavedSortStoredByDatastore() {
        ComplexSort sort = deserializer.deserialize(
                '{"id":5,"lookup":"datastore:person","sorts":"[{\\"field\\":\\"name\\",\\"descending\\":true}]"}',
                ComplexSort)

        assert sort.sorts.size() == 1
        assert sort.sorts[0].field == "name"
        assert sort.sorts[0].descending
    }

    @Test
    void testSavedAggregationStoredByDatastore() {
        Aggregation aggregation = deserializer.deserialize(
                '{"id":5,"lookup":"datastore:person","groupby":"[\\"age\\"]","functions":"[{\\"functionName\\":\\"sum\\",\\"field\\":\\"id\\"}]"}',
                Aggregation)

        assert aggregation.groupBy == ["age"]
        assert aggregation.functions.size() == 1
        assert aggregation.functions[0].functionName == "sum"
        assert aggregation.functions[0].field == "id"
    }

    @Test
    void testNativeShapeStillWorks() {
        ComplexSort sort = deserializer.deserialize(
                '{"id":"5","lookup":"datastore:person","sorts":[{"field":"name","descending":true}]}',
                ComplexSort)

        assert sort.sorts.size() == 1
        assert sort.sorts[0].field == "name"
    }

    @Test
    void testStringPropertiesAreNotUnflattened() {
        Search search = deserializer.deserialize('{"id":5,"lookup":"datastore:person","query":"[not json]"}', Search)

        assert search.query == "[not json]"
        assert search.lookup == "datastore:person"
    }

    @Test
    void testUnknownPropertiesAreIgnored() {
        Search search = deserializer.deserialize('{"id":5,"query":"abc","somethingElse":"ignored"}', Search)

        assert search.query == "abc"
    }
}
