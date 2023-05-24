package com.trevorism.data.bean

import com.trevorism.https.SecureHttpClient
import com.trevorism.https.SecureHttpClientBase

@jakarta.inject.Singleton
class PassThruSecureHttpClient extends SecureHttpClientBase implements SecureHttpClient{

    private CorrelationIdProvider correlationIdProvider

    PassThruSecureHttpClient(PassThruObtainTokenStrategy strategy, CorrelationIdProvider correlationIdProvider) {
        super(strategy)
        this.correlationIdProvider = correlationIdProvider
    }

    @Override
    protected Map<String, String> createHeaderMap() {
        Map<String, String> headersMap = new HashMap()
        headersMap.put(AUTHORIZATION, "bearer " + this.obtainTokenStrategy.getToken())
        headersMap.put("X-Correlation-Id", correlationIdProvider.getCorrelationId())
        return headersMap;
    }
}
