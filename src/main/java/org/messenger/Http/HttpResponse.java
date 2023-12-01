package org.messenger.Http;

import org.messenger.Errors.HttpError;

public class HttpResponse {
    private Object parsedObject;
    private int statusCode;
    private String responseMsg;


    public HttpResponse(Object parsedObject, int statusCode, String responseMsg, String contentType) {
        this.parsedObject = parsedObject;
        this.statusCode = statusCode;
        this.responseMsg = responseMsg;
    }

    public HttpResponse(HttpError error) {
        this.statusCode = error.getStatusCode();
        this.responseMsg = error.getStatusCodeMsg();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public Object getParsedObject() {
        return parsedObject;
    }

}
