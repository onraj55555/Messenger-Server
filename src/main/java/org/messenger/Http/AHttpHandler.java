package org.messenger.Http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.messenger.Errors.HttpError;
import org.messenger.Errors.MethodNotAllowedException;
import org.messenger.JSON.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public abstract class AHttpHandler implements HttpHandler {
    protected final String DEFAULT_ACCEPT_METHOD = HTTP.ACCEPT.JSON;
    protected final Logger logger;
    protected boolean exchangeHandled = false;

    protected HttpExchange exchange;
    private HTTP.STATUS status;
    protected HTTP.METHOD method;
    protected String acceptMethod;
    protected String contentType;

    public AHttpHandler(HTTP.STATUS status, HTTP.METHOD method) {
        this.status = status;
        this.method = method;
        logger = LogManager.getLogger(AHttpHandler.class);
    }

    public abstract HttpResponse specificHandle() throws HttpError;

    @Override
    public void handle(HttpExchange exchange) {
        try {
            try {
                logger.debug("Started handle");
                init(exchange);
                logger.debug("Inited myself");
                checkMethod();
                logger.debug("Method allowed");
                allowCors();
                logger.debug("Allowed CORS");
                HttpResponse response = specificHandle();
                logger.debug(response.toString());
                sendResponse(response);
                logger.debug("Sent response");
                quit();
                logger.debug("Quit");
            } catch (HttpError e) {
                logger.warn(e);
                sendResponse(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                quit();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void allowCors() {
        allowCors(true);
    }
    public void allowCors(boolean allowed) {
        if(allowed) {
            this.exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        }
    }

    public void init(HttpExchange exchange) {
        HttpExchangePool.addExchange(exchange);
        this.exchange = exchange;
    }

    public void quit() {
        exchange.close();
        HttpExchangePool.removeExchange(exchange);
    }

    @Deprecated
    public void wrongMethod() throws IOException {;
        String response = "Bad request, expected " + method + ", got " + exchange.getRequestMethod();
        logger.warn(response);
        exchange.sendResponseHeaders(400, response.length());
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
        exchange.close();
    }

    @Deprecated
    public void wrongContentType() throws IOException {
        String response = "Unsuppored conten type: " + contentType;
        logger.warn(response);
        exchange.sendResponseHeaders(415, response.length());
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
        exchange.close();
    }

    /*public void checkCors(HttpExchange exchange) throws IOException {
        if(checkMethod("OPTIONS")) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1); // No Content
            exchange.close();
        }
        HttpExchangePool.removeExchange(exchange);
    }*/

    private String getExchangeMethod() {
        return exchange.getRequestMethod();
    }

    private void checkMethod() throws HttpError {
        if(!this.method.getMethod().equalsIgnoreCase(getExchangeMethod())) {
            throw new MethodNotAllowedException("Expected " + method.getMethod() + ", got " + getExchangeMethod());
        }
    }

    /*public void handleRequest(HttpExchange exchange, String method) throws IOException {
        if(checkMethod(method)) {
            System.out.println(readBody(exchange));
        }
    }*/

    /*public void serverError(HttpExchange exchange, Exception e) throws IOException {
        serverLogger.error(e.getMessage());
        sendResponse(500, "text/plain", "Internal server error", exchange);
    }*/

    public String readBody() throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        return sb.toString().strip();
    }

    public void sendResponse(HttpError error) throws IOException {
        exchange.sendResponseHeaders(error.getStatusCode(), error.getLocalizedMessage().length());
        exchange.getResponseHeaders().set("Content-Type", HTTP.CONTENT_TYPE.TEXT);

        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(error.getLocalizedMessage().getBytes());
        responseBody.close();
        logger.debug("Response sent");
    }

    public void sendResponse(Object object) throws IOException {
        String jsonObj = JsonParser.stringify(object);
        int jsonObjLength = jsonObj.length();
        exchange.sendResponseHeaders(status.getCode(), jsonObjLength);

        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(jsonObj.getBytes());
        responseBody.close();
        logger.debug("Response sent");
    }

    public String getAccept() {
        return this.exchange.getRequestHeaders().getFirst("Accept");
    }

    public String getContentType() {
        return this.exchange.getRequestHeaders().getFirst("Content-Type");
    }

    public boolean contentTypeMatch(HttpExchange exchange, String contentType) {
        return false;
    }
}
