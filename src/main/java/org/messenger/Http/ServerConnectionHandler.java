package org.messenger.Http;

import org.messenger.Encryption.ServerEncryption;
import org.messenger.Errors.HttpError;
import org.messenger.Errors.ServerError;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Deprecated
public class ServerConnectionHandler extends AHttpHandler {
    public ServerConnectionHandler() {
        super(new HTTP.OK(), "GET");
    }
    @Override
    public HttpResponse specificHandle() throws IOException {
        logger.debug("Server connection request");
        if(checkMethod(HTTP.METHOD.GET)) {
            String publicKeyString;
            try {
                if("null".equals(acceptMethod)) {
                    acceptMethod = DEFAULT_ACCEPT_METHOD;
                }
                logger.debug("Accept type is + " + acceptMethod);

                try {
                    publicKeyString = ServerEncryption.getInstance().toString(acceptMethod);
                    HttpResponse response = new HttpResponse(HTTP.STATUS.OK, publicKeyString, acceptMethod);
                    logger.debug("Server connection handled");
                    return response;
                }
                catch (HttpError error) {

                    logger.error("Error whilest getting public key");
                    return new HttpResponse(error);
                }
            }
            catch (NoSuchAlgorithmException e) {
                logger.error("FATAL ERROR");
                return new HttpResponse(new ServerError());
                //sendResponse(serverError.getStatusCode(), HTTP.ACCEPT.TEXT, serverError.getStatusCodeMsg());
                //return;
            }
        }
        return new HttpResponse(new HttpError("Wrong method, expected GET"));
    }

    /*@Override
    public void handle(HttpExchange exchange) throws IOException {
        serverLogger.log("Server connection request");
        checkCors(exchange);
        if(checkMethod("GET")) {
            String publicKeyString;
            try {
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

                String responseContentType = exchange.getRequestHeaders().getFirst("Accept");

                if(responseContentType.equals("null")) {
                    responseContentType = HTTP.ACCEPT.JSON;
                }

                try {
                    publicKeyString = ServerEncryption.getInstance().toString(responseContentType);
                }
                catch (Error error) {
                    sendResponse(error.getStatusCode(), "text/plain", error.getMessage(), exchange);
                    serverLogger.error("Error whilest getting public key");
                    return;
                }

                sendResponse(HTTP.STATUS.OK, responseContentType, publicKeyString, exchange);

                serverLogger.log("Key request handled");
            } catch (NoSuchAlgorithmException e) {
                ServerError serverError = new ServerError();
                sendResponse(serverError.getStatusCode(), HTTP.ACCEPT.TEXT, serverError.getStatusCodeMsg(), exchange);
                return;
            }
        }
        else {
            wrongMethod(exchange, "GET");
        }
    }*/
}
