package com.trevorism.gcloud.webapi.model.exception

import javax.ws.rs.WebApplicationException

class UnparseableDateException extends WebApplicationException {

    UnparseableDateException(final String message){
        super(message, 400)
    }

    UnparseableDateException(final String message, final Throwable cause){
        super(message, cause, 400)
    }
}
