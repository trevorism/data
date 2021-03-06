package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.model.paging.Page
import com.trevorism.gcloud.webapi.service.FilterService
import com.trevorism.gcloud.webapi.service.PagingService
import com.trevorism.gcloud.webapi.service.filter.InMemoryFilterService
import com.trevorism.gcloud.webapi.service.paging.InMemoryPagingService
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

@Api("Filter Operations")
@Path("filter")
class FilterController {

    private FilterService filterService = new InMemoryFilterService()
    private Repository<ComplexFilter> repo = new PingingDatastoreRepository<>(ComplexFilter)

    @ApiOperation(value = "Perform a data operation and get a result **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def operate(ComplexFilter filter){
        filterService.filter(filter)
    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def operateById(@PathParam("id") String id){
        def complexFilter = repo.get(id)
        operate(complexFilter)
    }
}
