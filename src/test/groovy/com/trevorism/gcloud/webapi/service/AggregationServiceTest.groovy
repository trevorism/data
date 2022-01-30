package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.model.aggregating.AggregationConstants
import com.trevorism.gcloud.webapi.model.aggregating.AggregationFunction
import com.trevorism.gcloud.webapi.service.aggregate.InMemoryAggregationService
import com.trevorism.gcloud.webapi.service.lookup.LookupService
import org.junit.Test

class AggregationServiceTest {

    @Test
    void testAggregation() {
        InMemoryAggregationService service = new InMemoryAggregationService()
        service.lookupService = { createFakeData() } as LookupService

        def result = service.aggregate(
                new Aggregation(groupBy: ["age"],
                        functions: [
                                new AggregationFunction(field: "name", functionName: AggregationConstants.FIRST),
                                new AggregationFunction(field: "id", functionName: AggregationConstants.MAX),
                                new AggregationFunction(field: "name", functionName: AggregationConstants.COUNT),
                                new AggregationFunction(field: "id", functionName: AggregationConstants.SUM),
                                new AggregationFunction(field: "name", functionName: AggregationConstants.MIN),
                                new AggregationFunction(field: "id", functionName: AggregationConstants.AVERAGE),
                        ]
                ))

        assert result.size() == 3

        assert result[0].age == 10
        assert result[0].first_name == "Ren"
        assert result[0].max_id == 2
        assert result[0].count_name == 2
        assert result[0].sum_id == 3
        assert result[0].min_name == "Ren"
        assert result[0].average_id == 1.5

        assert result[1].age == 20
        assert result[1].first_name == "Trev"
        assert result[1].max_id == 3
        assert result[1].count_name == 1
        assert result[1].sum_id == 3
        assert result[1].min_name == "Trev"
        assert result[1].average_id == 3

        assert result[2].age == 22
        assert result[2].first_name == "Ism"
        assert result[2].max_id == 4
        assert result[2].count_name == 1
        assert result[2].sum_id == 4
        assert result[2].min_name == "Ism"
        assert result[2].average_id == 4

    }

    @Test
    void testNullEmptyAggregation() {
        InMemoryAggregationService service = new InMemoryAggregationService()
        service.lookupService = { createFakeData() } as LookupService
        assert service.aggregate(new Aggregation(lookup: "test")).size() == 4

    }

    private List<Map> createFakeData(){
        def list = []
        list << [id:1, age: 10, name: "Ren"]
        list << [id:2, age: 10, name: "Stimpy"]
        list << [id:3, age: 20, name: "Trev"]
        list << [id:4, age: 22, name: "Ism"]
        return list
    }
}
