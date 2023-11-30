package org.messenger.Errors;

import org.messenger.Http.HTTP;

public class InvalidMessageError extends HttpError {
    public InvalidMessageError(String msg) {
        super(msg);
        statusCode = HTTP.STATUS.BAD_REQUEST;
        statusCodeMsg = HTTP.STATUS.BAD_REQUEST_MSG;
    }
}
