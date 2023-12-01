package org.messenger.Errors;

public class HttpError extends Exception {
    protected int statusCode;
    protected String statusCodeMsg;

    HttpError() {
        super();
    }

    HttpError(String errorMsg) {
        super(errorMsg);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusCodeMsg() {
        return statusCodeMsg;
    }
}
