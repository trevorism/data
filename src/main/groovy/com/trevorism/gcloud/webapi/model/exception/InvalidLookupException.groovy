package com.trevorism.gcloud.webapi.model.exception

import javax.ws.rs.WebApplicationException

class InvalidLookupException extends WebApplicationException {

    InvalidLookupException(final String message){
        super(message, 400)
    }

    InvalidLookupException(final String message, final Throwable cause){
        super(message, cause, 400)
    }
}
