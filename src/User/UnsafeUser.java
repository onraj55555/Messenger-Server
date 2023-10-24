package User;

public class UnsafeUser extends User {
    private String password;
    public UnsafeUser(String username, String password, String key, String iv) {
        super(username, key, iv);
        this.password = password;
    }

    public User getSafeUser(Token token) {
        return (User) this;
    }

    public String getPassword() {
        return password;
    }
}
