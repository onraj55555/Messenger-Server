package org.messenger.Errors;

public class NotFoundException extends HttpError {
    public NotFoundException() {
        super();
        this.statusCode = 404;
        this.statusCodeMsg = "Not found";
    }

    public NotFoundException(String errorMsg) {
        super(errorMsg);
        this.statusCode = 404;
        this.statusCodeMsg = "Not found";
    }
}
