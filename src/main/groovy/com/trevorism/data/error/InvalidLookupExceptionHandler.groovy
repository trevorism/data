package com.trevorism.data.error

import com.trevorism.data.model.exception.InvalidLookupException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
class InvalidLookupExceptionHandler implements ExceptionHandler<InvalidLookupException, HttpResponse> {

    @Override
    HttpResponse handle(HttpRequest request, InvalidLookupException exception) {
        return HttpResponse.badRequest([message: exception.message])
    }
}
