package org.messenger.Errors;

import org.messenger.Http.HTTP;

public class InternalServerException extends HttpError {
    public InternalServerException() {
        super(new HTTP.INTERNAL_SERVER_ERROR());
    }

    public InternalServerException(String errorMsg) {
        super(new HTTP.INTERNAL_SERVER_ERROR(), errorMsg);
    }
}
