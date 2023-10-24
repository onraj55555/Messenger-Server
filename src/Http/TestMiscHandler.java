package Http;

import User.Token;
import User.TokenGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestMiscHandler extends AHttpHandler {
    @Override
    public HttpResponse specificHandle() throws IOException {
        serverLogger.working("Server misc test request");
        System.err.println(exchange.getRequestMethod());
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            System.err.println(contentType);

            InputStream inputStream = exchange.getRequestBody();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            System.err.println(bufferedReader.readLine());

            Token token = TokenGenerator.generate();
            return new HttpResponse(HTTP.STATUS.OK, token.toString(), HTTP.CONTENT_TYPE.TEXT);
    }
}
