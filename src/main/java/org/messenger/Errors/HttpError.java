package org.messenger.Errors;

import org.messenger.Http.HTTP;

public class HttpError extends Exception {
    protected HTTP.STATUS status;

    HttpError(HTTP.STATUS status) {
        super();
        this.status = status;
    }

    HttpError(HTTP.STATUS status, String errorMsg) {
        super(errorMsg);
        this.status = status;
    }

    public int getStatusCode() {
        return status.getCode();
    }

    public String getStatusCodeMsg() {
        return status.getMsg();
    }
}
