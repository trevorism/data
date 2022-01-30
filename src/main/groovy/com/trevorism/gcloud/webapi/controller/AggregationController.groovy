package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.aggregating.Aggregation
import com.trevorism.gcloud.webapi.model.filtering.ComplexFilter
import com.trevorism.gcloud.webapi.service.AggregationService
import com.trevorism.gcloud.webapi.service.FilterService
import com.trevorism.gcloud.webapi.service.aggregate.InMemoryAggregationService
import com.trevorism.gcloud.webapi.service.filter.InMemoryFilterService
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

@Api("Aggregation Operations")
@Path("aggregation")
class AggregationController {

    private AggregationService service = new InMemoryAggregationService()
    private Repository<Aggregation> repo = new PingingDatastoreRepository<>(Aggregation)

    @ApiOperation(value = "Perform a data operation and get a result **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def operate(Aggregation aggregation){
        service.aggregate(aggregation)
    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def operateById(@PathParam("id") String id){
        def aggregation = repo.get(id)
        operate(aggregation)
    }
}
