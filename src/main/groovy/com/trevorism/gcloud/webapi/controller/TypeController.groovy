package com.trevorism.gcloud.webapi.controller

import io.swagger.annotations.Api

import javax.ws.rs.Path

@Api("Type Operations")
@Path("type")
class TypeController {
    String id
    String type
    String repositoryId
}
