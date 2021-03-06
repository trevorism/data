package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.SingleDatasourceRequest
import com.trevorism.gcloud.webapi.model.describing.Describe
import com.trevorism.gcloud.webapi.model.searching.Search
import com.trevorism.gcloud.webapi.service.DescribeService
import com.trevorism.gcloud.webapi.service.describe.InMemoryDescribeService
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

@Api("Describe Operations")
@Path("describe")
class DescribeController {

    private DescribeService service = new InMemoryDescribeService()

    @ApiOperation(value = "Perform a data operation and get a result **Secure")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def operate(Describe query){
        service.describe(null)
    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def operateById(@PathParam("id") String id){
        service.describe(null)
    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    def describe(){
        service.describe(null)
    }
}
