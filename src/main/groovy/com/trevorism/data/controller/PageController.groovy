package com.trevorism.data.controller

import com.trevorism.data.DataUtils
import com.trevorism.data.model.paging.Page
import com.trevorism.data.service.PagingService
import com.trevorism.data.service.paging.InMemoryPagingService
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

@Controller("/page")
class PageController {

    private PagingService pagingService
    private SecureHttpClient httpClient

    PageController(SecureHttpClient passThruSecureHttpClient) {
        this.httpClient = passThruSecureHttpClient
        this.pagingService = new InMemoryPagingService(httpClient)
    }

    @Tag(name = "Page Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def operate(@Body Page paging) {
        pagingService.page(paging)
    }

    @Tag(name = "Page Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def operateById(String id) {
        Page paging = DataUtils.getById(id, Page)
        operate(paging)
    }
}
