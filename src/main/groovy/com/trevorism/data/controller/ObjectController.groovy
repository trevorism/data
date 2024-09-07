package com.trevorism.data.controller

import com.trevorism.data.DataUtils
import com.trevorism.https.SecureHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.hc.client5.http.HttpResponseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/object")
class ObjectController {

    private static final Logger log = LoggerFactory.getLogger(ObjectController.class.name)
    private SecureHttpClient httpClient

    private static final String DATASTORE_OBJECT_BASE_URL = DataUtils.DATASTORE_BASE_URL + "/object"
    private static final String BIGQUERY_OBJECT_BASE_URL = "${DataUtils.BIGQUERY_BASE_URL}/object"
    private static final String MEMORY_OBJECT_BASE_URL = "${DataUtils.MEMORY_BASE_URL}/object"

    ObjectController(SecureHttpClient passThruSecureHttpClient) {
        this.httpClient = passThruSecureHttpClient
    }

    @Tag(name = "Object Operations")
    @Operation(summary = "Get all objects types **Secure")
    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def getTypes(@QueryValue Optional<String> datasource) {
        String baseUrl = getBaseUrl(datasource.orElse(null))
        return httpClient.get(baseUrl)
    }

    @Tag(name = "Object Operations")
    @Operation(summary = "Get an object of type {kind} with id {id} **Secure")
    @Get(value = "{kind}/{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def read(String kind, String id, @QueryValue Optional<String> datasource) {
        String baseUrl = getBaseUrl(datasource.orElse(null))
        return httpClient.get("$baseUrl/$kind/$id")
    }

    @Tag(name = "Object Operations")
    @Operation(summary = "Get all objects of type {kind} **Secure")
    @Get(value = "{kind}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def readAll(String kind, @QueryValue Optional<String> datasource) {
        String baseUrl = getBaseUrl(datasource.orElse(null))
        return httpClient.get("$baseUrl/$kind")
    }

    @Tag(name = "Object Operations")
    @Operation(summary = "Create an object of type {kind} **Secure")
    @Status(HttpStatus.CREATED)
    @Post(value = "{kind}", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def create(String kind, @Body Map<String, Object> data, @QueryValue Optional<String> datasource) {
        try {
            String baseUrl = getBaseUrl(datasource.orElse(null))
            return httpClient.post("$baseUrl/$kind", DataUtils.gson.toJson(data))
        } catch (Exception e) {
            log.error("Unable to create ${kind} object: ${data} :: ${e.getMessage()}", e)
            throw new HttpResponseException(400, e.message)
        }
    }

    @Tag(name = "Object Operations")
    @Operation(summary = "Update an object of type {kind} with id {id} **Secure")
    @Put(value = "{kind}/{id}", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def update(String kind, long id, @Body Map<String, Object> data, @QueryValue Optional<String> datasource) {
        try {
            String baseUrl = getBaseUrl(datasource.orElse(null))
            return httpClient.put("$baseUrl/$kind/$id", DataUtils.gson.toJson(data))
        } catch (Exception e) {
            log.error("Unable to create ${kind} object: ${data} :: ${e.getMessage()}")
            throw new HttpResponseException(400, e.message)
        }
    }

    @Tag(name = "Object Operations")
    @Operation(summary = "Delete an object of type {kind} with id {id} **Secure")
    @Delete(value = "{kind}/{id}", produces = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    def delete(String kind, long id, @QueryValue Optional<String> datasource) {
        String baseUrl = getBaseUrl(datasource.orElse(null))
        return httpClient.delete("$baseUrl/$kind/$id")
    }

    private static String getBaseUrl(String datasource) {
        switch (datasource?.toLowerCase()) {
            case "bigquery":
                return BIGQUERY_OBJECT_BASE_URL
            case "memory":
                return MEMORY_OBJECT_BASE_URL
            default:
                return DATASTORE_OBJECT_BASE_URL
        }
    }
}
