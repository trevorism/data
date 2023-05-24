package com.trevorism.data.controller

import com.trevorism.data.DataUtils
import com.trevorism.data.model.aggregating.Aggregation
import com.trevorism.data.service.AggregationService
import com.trevorism.data.service.aggregate.InMemoryAggregationService
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/aggregation")
class AggregationController {

    private AggregationService service
    private SecureHttpClient httpClient

    AggregationController(SecureHttpClient passThruSecureHttpClient) {
        this.httpClient = passThruSecureHttpClient
        this.service = new InMemoryAggregationService(httpClient)
    }

    @Tag(name = "Aggregation Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def operate(@Body Aggregation aggregation) {
        service.aggregate(aggregation)
    }

    @Tag(name = "Aggregation Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def operateById(String id) {
        def aggregation = DataUtils.getById(id, Aggregation)
        operate(aggregation)
    }
}
