package org.messenger.User;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TokenGenerator {
    private static final Logger logger = LogManager.getLogger(TokenGenerator.class);
    private static ArrayList<Token> tokens = new ArrayList<>();
    private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-+*/";
    private static int tokenLenght = 64;

    public static Token generate() {
        logger.debug("Generating token");
        StringBuilder shuffeledString = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < tokenLenght; i++) {
            shuffeledString.append(letters.charAt(random.nextInt(letters.length())));
        }
        Token token = new Token(shuffeledString.toString());
        if(tokens.contains(token)) {
            token = generate();
        }
        logger.debug("Generated token");
        logger.debug("Token: " + token);
        return token;
    }

    private static void removeToken(Token token) {
        tokens.remove(token);
    }

    public static Token testToken() {
        logger.debug("Testing token generation");
        Token token = generate();
        removeToken(token);
        return token;
    }
}
