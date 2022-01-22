package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import com.trevorism.gcloud.webapi.model.Query
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient
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

@Api("Query Operations")
@Path("query")
class QueryController {

    private final SecureHttpClient client = new DefaultSecureHttpClient();
    private final Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    @ApiOperation(value = "Perform a data operation and get a result **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def operate(Query query){

    }

    @ApiOperation(value = "Get results of a saved data operation **Secure")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def operateById(@PathParam("id") String id){

    }
}
