package org.messenger.Errors;

import org.messenger.Http.HTTP;

@Deprecated
public class ParseError extends HttpError {
    public ParseError(String msg) {
        super(msg);
        //statusCode = HTTP.STATUS.CONFLICT;
        //statusCodeMsg = HTTP.STATUS.CONFLICT_MSG;
    }
}
