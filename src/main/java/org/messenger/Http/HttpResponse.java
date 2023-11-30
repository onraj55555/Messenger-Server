package org.messenger.Http;

import org.messenger.Annotations.JsonElement;
import org.messenger.Annotations.JsonSerializable;
import org.messenger.Errors.Error;

public class HttpResponse {
    private Object parsedObject;
    private int statusCode;
    private String responseMsg;


    public HttpResponse(Object parsedObject, int statusCode, String responseMsg, String contentType) {
        this.parsedObject = parsedObject;
        this.statusCode = statusCode;
        this.responseMsg = responseMsg;
    }

    public HttpResponse(Error error) {
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
