package com.trevorism.data.provider

import com.trevorism.http.HeadersHttpResponse
import com.trevorism.https.SecureHttpClient
import com.trevorism.https.token.ObtainTokenFromParameter
import io.micronaut.http.HttpRequest
import io.micronaut.runtime.http.scope.RequestAware
import io.micronaut.runtime.http.scope.RequestScope

@RequestScope
class PassThruObtainTokenStrategy extends ObtainTokenFromParameter implements RequestAware {

    @Override
    void setRequest(HttpRequest<?> request) {
        String authValue = request.getHeaders().get("Authorization")

        if(authValue == null) {
            setToken("")
        }
        else{
            String tokenValue = authValue.substring(SecureHttpClient.BEARER_.length())
            setToken(tokenValue)
        }
    }
}
