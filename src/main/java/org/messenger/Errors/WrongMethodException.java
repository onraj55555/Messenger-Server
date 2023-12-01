package org.messenger.Errors;

public class WrongMethodException extends HttpError {
    public WrongMethodException() {
        super();
        this.statusCode = 401;
        this.statusCodeMsg = "Wrong method";
    }

    public WrongMethodException(String errorMsg) {
        super(errorMsg);
        this.statusCode = 401;
        this.statusCodeMsg = "Wrong method";
    }
}
