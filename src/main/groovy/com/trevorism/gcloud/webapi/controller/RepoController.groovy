package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.filter.Created
import com.trevorism.gcloud.webapi.model.Repo
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

@Api("Repository Operations")
@Path("repo")
class RepoController {
    private static final Logger log = Logger.getLogger(RepoController.class.name)
    private Repository<Repo> repoRepository = new PingingDatastoreRepository<>(Repo)

    @ApiOperation(value = "Get a repo with id {id}")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Repo read(@PathParam("id") long id){
        repoRepository.get(String.valueOf(id))
    }

    @ApiOperation(value = "Get all repos")
    @GET
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    List<Repo> readAll(){
        repoRepository.list()
    }

    @ApiOperation(value = "Create a repo **Secure")
    @POST
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Created
    Repo create(Repo repo){
        try {
            repoRepository.create(repo)
        }catch (Exception e){
            log.severe("Unable to create Repo object: ${repo} :: ${e.getMessage()}")
            throw new BadRequestException(e)
        }
    }

    @ApiOperation(value = "Update a repo with id {id} **Secure")
    @PUT
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Repo update(@PathParam("id") long id, Repo repo){
        repoRepository.update(String.valueOf(id), repo)
    }

    @ApiOperation(value = "Delete a repo with id {id} **Secure")
    @DELETE
    @Secure(value = Roles.SYSTEM, allowInternal = true)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Repo delete(@PathParam("id") long id){
        repoRepository.delete(String.valueOf(id))
    }
}
