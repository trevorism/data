package com.trevorism.data.controller


import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.data.model.filtering.ComplexFilter
import com.trevorism.data.service.FilterService
import com.trevorism.data.service.filter.InMemoryFilterService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/filter")
class FilterController {

    private FilterService filterService = new InMemoryFilterService()
    private Repository<ComplexFilter> repo = new PingingDatastoreRepository<>(ComplexFilter)

    @Tag(name = "Filter Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operate(@Body ComplexFilter filter) {
        filterService.filter(filter)
    }

    @Tag(name = "Filter Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operateById(String id) {
        def complexFilter = repo.get(id)
        operate(complexFilter)
    }
}
