package org.messenger.DB;

import org.messenger.User.*;

public interface IUserDAO {
    UnsafeUser getUser(String username);
    UnsafeUser removeUser(String username);
    UnsafeUser removeUser(UnsafeUser user);
    UnsafeUser addUser(User user);
    UnsafeUser updateUser(UnsafeUser user, String newUsername);
    // Password update, ...
}
