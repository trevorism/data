package com.trevorism.gcloud.webapi.controller

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.filter.Created
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient
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

@Api("Object Operations")
@Path("object")
class ObjectController {

    private static final Logger log = Logger.getLogger(ObjectController.class.name)
    private SecureHttpClient httpClient = new DefaultSecureHttpClient()
    private static final String baseUrl = "https://datastore.trevorism.com/api"
    private Gson gson = new Gson()

    @ApiOperation(value = "Get an object of type {kind} with id {id}")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{kind}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def read(@PathParam("kind") String kind, @PathParam("id") String id){
        return httpClient.get("$baseUrl/$kind/$id")
    }

    @ApiOperation(value = "Get all objects of type {kind}")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{kind}")
    @Produces(MediaType.APPLICATION_JSON)
    def readAll(@PathParam("kind") String kind){
        httpClient.get("$baseUrl/$kind")
    }

    @ApiOperation(value = "Create an object of type {kind} **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{kind}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Created
    def create(@PathParam("kind") String kind, Map<String, Object> data){
        try {
            return httpClient.post("$baseUrl/$kind", gson.toJson(data))
        }catch (Exception e){
            log.severe("Unable to create ${kind} object: ${data} :: ${e.getMessage()}")
            throw new BadRequestException(e)
        }
    }

    @ApiOperation(value = "Update an object of type {kind} with id {id} **Secure")
    @PUT
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{kind}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def update(@PathParam("kind") String kind, @PathParam("id") long id, Map<String, Object> data){
        try {
            return httpClient.put("$baseUrl/$kind/$id", gson.toJson(data))
        }catch (Exception e){
            log.severe("Unable to create ${kind} object: ${data} :: ${e.getMessage()}")
            throw new BadRequestException(e)
        }
    }

    @ApiOperation(value = "Delete an object of type {kind} with id {id} **Secure")
    @DELETE
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{kind}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    def delete(@PathParam("kind") String kind, @PathParam("id") long id){
        return httpClient.delete("$baseUrl/$kind/$id")
    }
}
