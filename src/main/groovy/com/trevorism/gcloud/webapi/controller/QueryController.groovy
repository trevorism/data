package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.Query
import com.trevorism.gcloud.webapi.service.QueryService
import com.trevorism.gcloud.webapi.service.query.InMemoryQueryService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Api("Query Operations")
@Path("query")
class QueryController {

    private QueryService queryService = new InMemoryQueryService()
    private Repository<Query> repo = new PingingDatastoreRepository<>(Query)

    @ApiOperation(value = "Perform a data operation and get a result **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def operate(Query query){
        queryService.query(query)
    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def operateById(@PathParam("id") String id){
        def query = repo.get(id)
        operate(query)
    }
}
