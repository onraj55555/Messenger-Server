package Errors;
import Http.HTTP;

public class InvalidAcceptType extends HttpError {
    public InvalidAcceptType(String acceptType) {
        super(acceptType + " is not a valid Accept type for this operation");
        statusCode = HTTP.STATUS.BAD_REQUEST;
        statusCodeMsg = HTTP.STATUS.BAD_REQUEST_MSG;
    }
}