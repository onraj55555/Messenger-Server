package org.messenger.DB;

import org.messenger.User.User;
import org.messenger.User.UnsafeUser;

import java.io.FileNotFoundException;

public class FileUserDAO implements IUserDAO {
    private FileUserDB fileUserDB;


    public FileUserDAO() throws FileNotFoundException {
        FileUserDB.init("UserDB.txt");
        fileUserDB = FileUserDB.getInstance();
    }
    @Override
    public User getUser(String username) {
        return fileUserDB.getUserUsername(username);
    }

    @Override
    public User removeUser(String username) {
        return null;
    }

    @Override
    public User updateUser(User user, String newUsername) {
        return null;
    }

    @Override
    public User addUser(UnsafeUser user) {
        return null;
    }

    @Override
    public User removeUser(User user) {
        return null;
    }
}
