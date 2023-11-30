package org.messenger.DB;

import org.messenger.User.UnsafeUser;

import java.io.FileNotFoundException;

public class FileUserDAO implements IUserDAO {
    private FileUserDB fileUserDB;


    public FileUserDAO() throws FileNotFoundException {
        FileUserDB.init("UserDB.txt");
        fileUserDB = FileUserDB.getInstance();
    }
    @Override
    public UnsafeUser getUser(String username) {
        return fileUserDB.getUserUsername(username);
    }

    @Override
    public UnsafeUser removeUser(String username) {
        return null;
    }

    @Override
    public UnsafeUser updateUser(UnsafeUser user, String newUsername) {
        return null;
    }

    @Override
    public UnsafeUser addUser(UnsafeUser user) {
        return null;
    }

    @Override
    public UnsafeUser removeUser(UnsafeUser user) {
        return null;
    }
}
