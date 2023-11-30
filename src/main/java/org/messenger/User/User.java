package org.messenger.User;

public class User extends UnsafeUser {
    private final String username;
    private final JWTToken jwt;

    public User(UnsafeUser unsafeUser) {
        this.username = unsafeUser.getUsername();
        jwt = new JWTToken(JWTGenerator.generate(this.username));
    }

    public User(String username) {
        this.username = username;
        jwt = new JWTToken(JWTGenerator.generate(this.username));
    }

    @Override
    public String getUsername() {
        return username;
    }

    public JWTToken getJwt() {
        return jwt;
    }
}
