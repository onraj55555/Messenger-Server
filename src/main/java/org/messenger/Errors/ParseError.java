package org.messenger.Errors;

import org.messenger.Http.HTTP;

public class ParseError extends Error {
    public ParseError(String msg) {
        super(msg);
        statusCode = HTTP.STATUS.CONFLICT;
        statusCodeMsg = HTTP.STATUS.CONFLICT_MSG;
    }
}
