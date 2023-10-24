package Errors;

public class ServerError extends Error {
    public ServerError() {
        super();
    }

    ServerError(String errorMsg) {
        super(errorMsg);
    }
}
