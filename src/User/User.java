package User;

import Encryption.AESClientEncryption;

import java.util.Objects;

public class User {
    private String username;
    private String key;
    private String iv;
    private Token token;

    private int uid = -1;

    private static int gid = 1;

    public User(int uid, String username, String key, String iv) {
        this.uid = uid;
        this.username = username;
        this.key = key;
        this.iv = iv;
    }

    protected User(String username, String key, String iv) {
        this.username = username;
        this.key = key;
        this.iv = iv;
    }

    public User(String username, String key, String iv, Token token) {
        this.username = username;
        this.key = key;
        this.iv = iv;
        this.token = token;
    }

    public User(int uid, String username, String key, String iv, Token token) {
        this.uid = uid;
        this.username = username;
        this.key = key;
        this.iv = iv;
        this.token = token;
    }

    public void setToken(Token token) {

    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public String getIv() { return iv; }

    public int getUid() {
        return uid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nUsername: ").append(username).append("\n").append("Key: ").append(key).append("\n").append("IV: ").append(iv).append("\n").append("Token: ").append(token);
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public String getTokenEncrypted() {
        try {
            return AESClientEncryption.encryptAES(token.toString(), this.key, this.iv);
        }
        catch (Exception e) {
            System.err.println("Failed to encrypt using AES");
            return "";
        }
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
