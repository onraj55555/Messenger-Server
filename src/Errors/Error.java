package Errors;

public class Error extends RuntimeException {
    protected int statusCode;
    protected String statusCodeMsg;

    Error() {
        super();
    }

    Error(String errorMsg) {
        super(errorMsg);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusCodeMsg() {
        return statusCodeMsg;
    }
}
