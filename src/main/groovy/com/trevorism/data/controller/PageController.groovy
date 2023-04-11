package com.trevorism.data.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.data.model.paging.Page
import com.trevorism.data.service.PagingService
import com.trevorism.data.service.paging.InMemoryPagingService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/page")
class PageController {

    private PagingService pagingService = new InMemoryPagingService()
    private Repository<Page> repo = new PingingDatastoreRepository<>(Page)

    @Tag(name = "Page Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operate(@Body Page paging) {
        pagingService.page(paging)
    }

    @Tag(name = "Page Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operateById(String id) {
        Page paging = repo.get(id)
        operate(paging)
    }
}
