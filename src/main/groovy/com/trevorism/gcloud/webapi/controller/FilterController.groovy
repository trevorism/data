package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.filter.Created
import com.trevorism.gcloud.webapi.model.Filter
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.BadRequestException
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.logging.Logger

@Api("Filter Operations")
@Path("filter")
class FilterController {
    private static final Logger log = Logger.getLogger(FilterController.class.name)

    @ApiOperation(value = "Get a filter with id {id}")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Filter read(@PathParam("id") long id){

    }

    @ApiOperation(value = "Get all filters")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    List<Filter> readAll(){

    }

    @ApiOperation(value = "Create a filter **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Created
    Filter create(Filter filter){
        try {

        }catch (Exception e){
            log.severe("Unable to create ${kind} object: ${data} :: ${e.getMessage()}")
            throw new BadRequestException(e)
        }
    }

    @ApiOperation(value = "Update a filter with id {id} **Secure")
    @PUT
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Filter update(@PathParam("id") long id, Filter filter){

    }

    @ApiOperation(value = "Delete a filter with id {id} **Secure")
    @DELETE
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Filter delete(@PathParam("id") long id){

    }
}
