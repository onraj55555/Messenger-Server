package User;

import java.util.HashMap;
import java.util.HashSet;

public class UserStorage {
    private static HashMap<String, User> users = new HashMap<>();

    public static User addUser(User user) {
        users.put(user.getUsername(), user);
        return users.get(user.getUsername());
    }

    public static User removeUser(User user) {
        user = users.remove(user.getUsername());
        return user;
    }
}
