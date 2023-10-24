package Http;

import Annotations.JsonElement;
import Annotations.JsonSerializable;
import Errors.Error;

@JsonSerializable
public class HttpResponse {
    @JsonElement
    private int statusCode;

    @JsonElement
    private String responseMsg;

    @JsonElement
    private String contentType;

    public HttpResponse(int statusCode, String responseMsg, String contentType) {
        this.statusCode = statusCode;
        this.responseMsg = responseMsg;
        this.contentType = contentType;
    }

    public HttpResponse(Error error) {
        this.statusCode = error.getStatusCode();
        this.responseMsg = error.getStatusCodeMsg();
        this.contentType = HTTP.CONTENT_TYPE.TEXT;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(statusCode).append(" ").append(responseMsg).append(" ").append(contentType);
        return sb.toString();
    }
}
