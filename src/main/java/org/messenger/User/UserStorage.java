package org.messenger.User;

import java.util.HashMap;

public class UserStorage {
    private static HashMap<String, UnsafeUser> users = new HashMap<>();

    public static UnsafeUser addUser(UnsafeUser user) {
        users.put(user.getUsername(), user);
        return users.get(user.getUsername());
    }

    public static UnsafeUser removeUser(UnsafeUser user) {
        user = users.remove(user.getUsername());
        return user;
    }
}
