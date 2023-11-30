package org.messenger.DB;

import org.messenger.User.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UnknownFormatConversionException;

public class FileUserDB {
    private static String dbFile;
    private static FileUserDB fileUserDB;
    private static int id = 1;
    private static final String delim = ";";

    public static void init(String file) throws FileNotFoundException {
        dbFile = file;
        Scanner sc = new Scanner(new File(dbFile));
        while(sc.hasNext()) {
            id++;
        }
    }

    public static FileUserDB getInstance() {
        if(fileUserDB == null) {
            fileUserDB = new FileUserDB();
        }
        return fileUserDB;
    }

    private String dbFormatter(UnsafeUser unsafeUser) {
         StringBuilder sb = new StringBuilder(unsafeUser.getUid()).append(delim).append(unsafeUser.getUsername()).append(delim).append(unsafeUser.getPassword()).append(delim).append(unsafeUser.getKey()).append(delim).append(unsafeUser.getIv());
         return sb.toString();
    }

    private int getUid(String line) {
        return Integer.parseInt(line.split(delim)[0]);
    }

    private String getUsername(String line) {
        return line.split(delim)[1];
    }

    private String getKey(String line) {
        return line.split(delim)[3];
    }

    private String getIV(String line) { return line.split(delim)[4]; }

    /* TODO
    User in db moet zijn eigen public key niet opslaan dus moet ook niet in db, moet wel opslaan in actieve users op ram
     */
    public User getUserUsername(String username) {
        Scanner sc;
        try {
            sc = new Scanner(new File(dbFile));
        } catch (FileNotFoundException e) {
            return null;
        }
        User user = null;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String currUsername = getUsername(line);
            if(username.equals(currUsername)) {
                user = new User(getUid(line), getUsername(line), getKey(line), getIV(line), TokenGenerator.generate());
                UserStorage.addUser(user);
                return user;
            }
        }
        return user;
    }

    public User putUser(UnsafeUser unsafeUser) {
        return null;
    }
}
