package com.trevorism.data.error

import com.trevorism.data.model.exception.InvalidLookupException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Test

class InvalidLookupExceptionHandlerTest {

    @Test
    void testInvalidLookupIsABadRequest() {
        InvalidLookupExceptionHandler handler = new InvalidLookupExceptionHandler()
        HttpResponse response = handler.handle(null, new InvalidLookupException("Unable to lookup dataset from: arbitrary"))
        assert HttpStatus.BAD_REQUEST == response.status
    }

    @Test
    void testTheReasonIsPassedToTheCaller() {
        InvalidLookupExceptionHandler handler = new InvalidLookupExceptionHandler()
        HttpResponse response = handler.handle(null, new InvalidLookupException("Unable to lookup dataset from: arbitrary"))
        assert response.body()["message"].contains("arbitrary")
    }
}
