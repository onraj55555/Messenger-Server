package DB;

import User.*;

public interface IUserDAO {
    User getUser(String username);
    User removeUser(String username);
    User removeUser(User user);
    User addUser(UnsafeUser user);
    User updateUser(User user, String newUsername);
    // Password update, ...
}
