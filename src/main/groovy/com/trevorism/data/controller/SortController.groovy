package com.trevorism.data.controller


import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.data.model.sorting.ComplexSort
import com.trevorism.data.service.SortService
import com.trevorism.data.service.sort.InMemorySortService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/sort")
class SortController {

    private SortService sortService = new InMemorySortService()
    private Repository<ComplexSort> repo = new PingingDatastoreRepository<>(ComplexSort)

    @Tag(name = "Sort Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operate(@Body ComplexSort complexSort) {
        sortService.sort(complexSort)
    }

    @Tag(name = "Sort Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operateById(String id) {
        ComplexSort complexSort = repo.get(id)
        operate(complexSort)
    }
}
