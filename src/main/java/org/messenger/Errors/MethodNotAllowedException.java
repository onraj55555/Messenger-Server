package org.messenger.Errors;

import org.messenger.Http.HTTP;

public class MethodNotAllowedException extends HttpError {
    public MethodNotAllowedException() {
        super(new HTTP.METHOD_NOT_ALLOWED());
    }

    public MethodNotAllowedException(String errorMsg) {
        super(new HTTP.METHOD_NOT_ALLOWED(), errorMsg);
    }
}
