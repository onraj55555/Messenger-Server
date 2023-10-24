package Http;

import Command.ACommand;
import Command.CommandParseException;
import Command.CommandParser;
import Command.LoginCommand;
import Encryption.ServerEncryption;
import Errors.HttpError;
import Errors.InvalidMessageError;
import Errors.ServerError;
import User.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ServerLoginHandler extends AHttpHandler {

    @Override
    public HttpResponse specificHandle() throws IOException {
        serverLogger.working("Server login request");
        if(checkMethod(HTTP.METHOD.POST)) {
            if(acceptMethod.equals("null")) {
                acceptMethod = DEFAULT_ACCEPT_METHOD;
            }

            serverLogger.working("Right content type");

            String body = readBody();

            serverLogger.working("Body: " + body);

            String jsonBody;

            try {
                jsonBody = ServerEncryption.getInstance().decode(body);
            }
            catch (NoSuchAlgorithmException e) {
                return new HttpResponse(new ServerError());

            }
            catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                    NoSuchPaddingException e) {
                return new HttpResponse(new InvalidMessageError("Could not decode login body, perhaps it's wrongly encrypted?"));
            }

            serverLogger.working("Decrypted body");
            serverLogger.info(jsonBody);

            ACommand loginCommand;

            try {
                loginCommand = CommandParser.parse(jsonBody);
            }
            catch (CommandParseException e) {
                return new HttpResponse(new InvalidMessageError("Failed to parse decrypted body"));
            }

            serverLogger.working("Parsed command");

            if(!(loginCommand instanceof LoginCommand)) {
                return new HttpResponse(new InvalidMessageError("Wrong type"));
            }

            User user = null;
            try {
                user = loginCommand.makeUser();
            }
            catch (IllegalArgumentException e) {
                return new HttpResponse(new InvalidMessageError(e.getMessage()));
            }

            serverLogger.error(user.toString());

            HttpResponse response = new HttpResponse(HTTP.STATUS.OK, user.getTokenEncrypted(), acceptMethod);
            return response;
        }
        return new HttpResponse(new HttpError("Wrong method, expected GET"));
    }

    /*@Override
    public void handle(HttpExchange exchange) throws IOException {

        if(checkMethod(exchange, "POST")) {
            serverLogger.log("Accepted login request");

            allowCors();

            String contentType = getAccept(exchange);

            if(!contentType.equals("text/plain")) {
                wrongContentType(exchange, contentType);
                return;
            }



            InputStream inputStream = exchange.getRequestBody();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String body = bufferedReader.readLine();

            System.err.println(body);

            String jsonString = null;
            try {
                jsonString = ServerEncryption.getInstance().decode(body);
            }
            catch (Exception ignored) {
            }



            ACommand aCommand = null;
            try {
                aCommand = CommandParser.parse(jsonString);
            }
            catch (CommandParseException commandParseException) {
                serverLogger.error("Failed to parse command");
                wrongMethod(exchange, commandParseException.toString());
                return;
            }

            serverLogger.log("Parsed command");

            if(!(aCommand instanceof LoginCommand)) {
                wrongMethod(exchange, aCommand.getParamValue("type"));
                return;
            }

            serverLogger.log("User command valid");

            User user = aCommand.makeUser();

            String encrypted_token = null; // = user.getToken().toString();
            try {
                //encrypted_token = AESClientEncryption.encryptAES(user.getToken().getToken(), user.getKey(), user.getIv());
            } catch (Exception e) {
                serverLogger.error(e.toString());
                throw new RuntimeException(e);
            }
            //TOKEN ENCRYPTEREN!!!
            sendResponse(200, "text/plain", encrypted_token, exchange);
            serverLogger.error(encrypted_token);
        }
    }*/
}
