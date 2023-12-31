package org.messenger.Http;

import org.messenger.Debug.ServerLogger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.messenger.Errors.*;
import org.messenger.Errors.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public abstract class AHttpHandler implements HttpHandler {
    protected final String DEFAULT_ACCEPT_METHOD = HTTP.ACCEPT.JSON;
    protected final Logger logger;
    protected boolean exchangeHandled = false;

    protected HttpExchange exchange;

    protected String method;
    protected String acceptMethod;
    protected String contentType;

    public AHttpHandler() {
        logger = LogManager.getLogger(AHttpHandler.class);
    }

    public abstract HttpResponse specificHandle() throws IOException;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.debug("Started handle");
        init(exchange);
        logger.debug("Inited myself");
        allowCors();
        logger.debug("Allowed CORS");
        HttpResponse response = specificHandle();
        logger.debug(response.toString());
        sendResponse(response);
        logger.debug("Sent response");
        quit();
        logger.debug("Quit");
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
        this.method = this.exchange.getRequestMethod().toLowerCase();
        this.acceptMethod = getAccept();
        this.contentType = getContentType();
    }

    public void quit() {
        HttpExchangePool.removeExchange(exchange);
    }

    public void wrongMethod() throws IOException {;
        String response = "Bad request, expected " + method + ", got " + exchange.getRequestMethod();
        logger.warn(response);
        exchange.sendResponseHeaders(400, response.length());
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
        exchange.close();
    }

    public void wrongContentType() throws IOException {
        String response = "Unsuppored conten type: " + contentType;
        logger.warn(response);
        exchange.sendResponseHeaders(415, response.length());
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
        exchange.close();
    }

    public void checkCors(HttpExchange exchange) throws IOException {
        if(checkMethod("OPTIONS")) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1); // No Content
            exchange.close();
        }
        HttpExchangePool.removeExchange(exchange);
    }

    public boolean checkMethod(String method) throws IOException {
        if(method.equalsIgnoreCase(this.exchange.getRequestMethod())) {
            logger.warn("Accepted " + method + " request");
            exchangeHandled = true;
            return true;
        }
        return false;
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

    public void sendResponse(int code, String contentType, String msg) throws IOException {
        logger.debug("Sending response");
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(code, msg.length());

        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(msg.getBytes());
        responseBody.close();
        exchange.close();
        logger.debug("Response sent");
        HttpExchangePool.removeExchange(exchange);
    }

    public void sendResponse(int code, int length) throws IOException {
        logger.debug("Sending response");
        exchange.sendResponseHeaders(code, length);
        exchange.close();
        logger.debug("Response sent");
        HttpExchangePool.removeExchange(exchange);
    }

    public void sendResponse(Error error) throws IOException {
        sendResponse(error.getStatusCode(), "text/plain", error.getStatusCodeMsg());
    }

    public void sendResponse(HttpResponse response) throws IOException {
        sendResponse(response.getStatusCode(), response.getContentType(), response.getResponseMsg());
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
