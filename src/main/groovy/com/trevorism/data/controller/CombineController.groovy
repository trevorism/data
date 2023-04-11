package com.trevorism.data.controller

import com.trevorism.data.model.combining.Join
import com.trevorism.data.model.combining.SetOperation
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag


@Controller("/combine")
class CombineController {

    @Tag(name = "Combine Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/join", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def join(@Body Join join) {

    }

    @Tag(name = "Combine Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/intersection", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def intersect(@Body SetOperation intersection) {

    }

    @Tag(name = "Combine Operations")
    @Operation(summary = "Perform a data operation and get a result **Secure")
    @Post(value = "/union", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def union(@Body SetOperation union) {

    }

    @Operation(summary = "Get results of a saved data operation **Secure")
    @Get(value = "{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    def operateById(String id) {

    }
}
