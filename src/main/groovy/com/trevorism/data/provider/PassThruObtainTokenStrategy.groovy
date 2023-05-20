package com.trevorism.data.provider

import com.trevorism.https.token.ObtainTokenFromParameter
import io.micronaut.http.HttpRequest
import io.micronaut.runtime.http.scope.RequestAware
import io.micronaut.runtime.http.scope.RequestScope

@RequestScope
class PassThruObtainTokenStrategy extends ObtainTokenFromParameter implements RequestAware {

    @Override
    void setRequest(HttpRequest<?> request) {
        String authValue = request.getHeaders().get("Authorization")
        String tokenValue = authValue.substring("Bearer ".length())
        setToken(tokenValue)
    }
}
