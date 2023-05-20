package com.trevorism.data.provider

import com.trevorism.https.SecureHttpClient
import com.trevorism.https.SecureHttpClientBase

@jakarta.inject.Singleton
class PassThruSecureHttpClient extends SecureHttpClientBase implements SecureHttpClient{
    PassThruSecureHttpClient(PassThruObtainTokenStrategy strategy) {
        super(strategy)
    }
}
