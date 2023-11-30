package org.messenger.Http;

public class HTTP {
    public static class ACCEPT {
        public static final String TEXT = "text/plain";
        public static final String JSON = "application/json";
        public static final String XML = "application/xml";
    }

    public static class CONTENT_TYPE {
        public static final String TEXT = "text/plain";
    }

    public static class STATUS {
        public static final int BAD_REQUEST = 400;
        public static final String BAD_REQUEST_MSG = "Bad Request";

        public static final int OK = 200;
        public static final String OK_MSG = "OK";

        public static final int CONFLICT = 409;
        public static final String CONFLICT_MSG = "Conflict";
    }

    public static class METHOD {
        public static final String GET = "GET";
        public static final String POST = "POST";
    }
}
