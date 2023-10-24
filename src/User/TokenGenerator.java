package User;

import Debug.ServerLogger;

import java.util.*;

public class TokenGenerator {
    private static ArrayList<Token> tokens = new ArrayList<>();
    private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-+*/";
    private static int tokenLenght = 64;

    private static ServerLogger serverLogger = new ServerLogger(TokenGenerator.class.getName());

    public static Token generate() {
        serverLogger.working("Generating token");
        StringBuilder shuffeledString = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < tokenLenght; i++) {
            shuffeledString.append(letters.charAt(random.nextInt(letters.length())));
        }
        Token token = new Token(shuffeledString.toString());
        if(tokens.contains(token)) {
            token = generate();
        }
        serverLogger.working("Generated token");
        serverLogger.logProtected("Token: " + token);
        return token;
    }

    private static void removeToken(Token token) {
        tokens.remove(token);
    }

    public static Token testToken() {
        serverLogger.working("Testing token generation");
        Token token = generate();
        removeToken(token);
        return token;
    }
}
