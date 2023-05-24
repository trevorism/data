package com.trevorism.data.controller

import com.trevorism.data.DataUtils
import com.trevorism.data.model.searching.Search
import com.trevorism.data.service.SearchService
import com.trevorism.data.service.query.InMemoryQueryService
import com.trevorism.data.service.search.InMemorySearchService
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


@Controller("/search")
class SearchController {

    private SearchService searchService

    SearchController(SecureHttpClient passThruSecureHttpClient) {
        searchService = new InMemorySearchService(passThruSecureHttpClient)
    }

    @Tag(name = "Search Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operate(@Body Search query) {
        searchService.search(query)
    }

    @Tag(name = "Search Operations")
    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operateById(String id) {
        def query = DataUtils.getById(id, Search)
        operate(query)
    }
}
