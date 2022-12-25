package com.trevorism.gcloud.webapi.model.exception

import javax.ws.rs.WebApplicationException

class UnparseableDateException extends WebApplicationException {

    UnparseableDateException(final String message, final Throwable cause, final int status){
        super(message, cause, status)
    }

    UnparseableDateException(final String message){
        super(message)
    }

    UnparseableDateException(final String message, final Throwable cause){
        super(message, cause, 400)
    }
}
