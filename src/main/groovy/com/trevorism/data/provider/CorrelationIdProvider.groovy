package com.trevorism.data.provider

import io.micronaut.http.HttpRequest
import io.micronaut.runtime.http.scope.RequestAware
import io.micronaut.runtime.http.scope.RequestScope

@RequestScope
class CorrelationIdProvider implements RequestAware{

    private String correlationId = UUID.randomUUID().toString()

    @Override
    void setRequest(HttpRequest<?> request) {
        Optional<String> attr = request.getAttribute("X-Correlation-Id", String)
        if(attr.isPresent()){
            correlationId = attr.get()
        }
    }

    String getCorrelationId(){
        return correlationId
    }
}
