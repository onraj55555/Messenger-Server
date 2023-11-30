package org.messenger.User;

public class JWTToken {
    private final String jwt;
    public JWTToken(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return jwt;
    }
}
