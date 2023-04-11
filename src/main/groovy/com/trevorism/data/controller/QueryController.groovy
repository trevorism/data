package com.trevorism.data.controller


import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.data.model.Query
import com.trevorism.data.service.QueryService
import com.trevorism.data.service.query.InMemoryQueryService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/query")
class QueryController {

    private QueryService queryService = new InMemoryQueryService()
    private Repository<Query> repo = new PingingDatastoreRepository<>(Query)

    @Tag(name = "Query Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operate(@Body Query query) {
        queryService.query(query)
    }

    @Tag(name = "Query Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operateById(String id) {
        def query = repo.get(id)
        operate(query)
    }
}
