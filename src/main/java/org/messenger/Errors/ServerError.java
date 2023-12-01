package org.messenger.Errors;

@Deprecated
public class ServerError extends HttpError {
    public ServerError() {
        super();
    }

    ServerError(String errorMsg) {
        super(errorMsg);
    }
}
