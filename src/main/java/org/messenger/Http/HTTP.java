package org.messenger.Http;

public class HTTP {
    public interface STATUS {
        int getCode();
        String getMsg();
    }

    public interface METHOD {
        String getMethod();
    }

    public static class GET implements METHOD {
        public String getMethod() {
            return "GET";
        }
    }
    public static class ACCEPT {
        public static final String TEXT = "text/plain";
        public static final String JSON = "application/json";
        public static final String XML = "application/xml";
    }

    public static class CONTENT_TYPE {
        public static final String TEXT = "text/plain";
    }

    public static class OK implements STATUS {
        public int getCode() {
            return 200;
        }

        public String getMsg() {
            return "OK";
        }
    }

    public static class INTERNAL_SERVER_ERROR implements STATUS {
        public int getCode() {
            return 500;
        }

        public String getMsg() {
            return "Internam server error";
        }
    }

    public static class NOT_FOUND implements STATUS {
        public int getCode() {
            return 404;
        }

        public String getMsg() {
            return "Not found";
        }
    }

    public static class METHOD_NOT_ALLOWED implements STATUS {
        public int getCode() {
            return 405;
        }

        public String getMsg() {
            return "Method not allowed";
        }
    }

    /*public static class STATUS {
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
    }*/
}
