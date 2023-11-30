package org.messenger.User;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class JWTGenerator {
    // Generate key for hashing
    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public static String generate(String username) {
        return Jwts.builder().subject(username).signWith(key).compact();
    }
}
