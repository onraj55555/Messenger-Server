package org.messenger.User;

import java.util.Objects;

public class Token {
    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return getToken();
    }

    public int length() {
        return token.length();
    }
}
