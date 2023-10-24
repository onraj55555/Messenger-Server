package Errors;

public class HttpError extends Error {
    public HttpError() {
        super();
    }

    public HttpError(String errorMsg) {
        super(errorMsg);
    }
}
