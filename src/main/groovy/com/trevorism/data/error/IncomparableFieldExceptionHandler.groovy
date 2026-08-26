package com.trevorism.data.error

import com.trevorism.data.model.exception.IncomparableFieldException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
class IncomparableFieldExceptionHandler implements ExceptionHandler<IncomparableFieldException, HttpResponse> {

    @Override
    HttpResponse handle(HttpRequest request, IncomparableFieldException exception) {
        return HttpResponse.badRequest([message: exception.message])
    }
}
