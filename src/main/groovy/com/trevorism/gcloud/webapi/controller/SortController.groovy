package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.sorting.ComplexSort
import com.trevorism.gcloud.webapi.service.SortService
import com.trevorism.gcloud.webapi.service.sort.InMemorySortService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Api("Sort Operations")
@Path("sort")
class SortController {

    private SortService sortService = new InMemorySortService()
    private Repository<ComplexSort> repo = new PingingDatastoreRepository<>(ComplexSort)

    @ApiOperation(value = "Perform a data operation and get a result **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def operate(ComplexSort complexSort){
        sortService.sort(complexSort)
    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def operateById(@PathParam("id") String id){
        ComplexSort complexSort = repo.get(id)
        operate(complexSort)
    }
}
