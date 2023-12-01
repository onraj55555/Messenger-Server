package org.messenger.Errors;

import org.messenger.Http.HTTP;

public class NotFoundException extends HttpError {
    public NotFoundException() {
        super(new HTTP.NOT_FOUND());
    }

    public NotFoundException(String errorMsg) {
        super(new HTTP.NOT_FOUND(), errorMsg);
    }
}
