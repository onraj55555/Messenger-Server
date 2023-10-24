package Http;

import Command.ACommand;
import Command.CommandParser;
import Encryption.ServerEncryption;
import Errors.HttpError;
import User.TokenGenerator;

import User.*;

import java.io.*;

public class TestHandler extends AHttpHandler {
    @Override
    public HttpResponse specificHandle() throws IOException {
        serverLogger.working("Server test request");
        if(checkMethod(HTTP.METHOD.POST)) {
            String body = readBody();

            System.err.println(contentType);


            if(contentType.equals("text/plain")) {
                try {
                    body = ServerEncryption.getInstance().decode(body);
                } catch (Exception ignored) {
                    System.err.println(ignored);
                }
            }

            System.err.println(body);

            try {
                ACommand aCommand = CommandParser.parse(body);
                System.out.println(aCommand);
                User temp = aCommand.makeUser();
                System.out.println(temp);
            }
            catch (Exception e) {
                System.err.println(e);
            }

            Token token = TokenGenerator.generate();
            HttpResponse response = new HttpResponse(HTTP.STATUS.OK, token.toString(), HTTP.CONTENT_TYPE.TEXT);
            return response;
        }
        return new HttpResponse(new HttpError("Wrong method, expected GET"));
    }
}
