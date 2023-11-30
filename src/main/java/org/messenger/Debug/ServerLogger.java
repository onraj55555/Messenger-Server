package org.messenger.Debug;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerLogger {
    public static boolean workInfo = true;
    public static boolean infoInfo = true;
    public static boolean errorInfo = true;
    public static boolean debugInfo = true;

    private final String id;
    private boolean protect;
    public ServerLogger(String id) {
        this.id = id;
        protect = false;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }

    public void working(String msg) {
        if(workInfo) System.out.println("W | " + getTime() + " " + id + " | " + msg);
    }

    public void info(String msg) {
        if(infoInfo) System.out.println("I | " + getTime() + " " + id + " | " + msg);
    }

    public void error(String msg) {
        if(errorInfo) System.err.println("E | " + getTime() + " " + id + " | " + msg);
    }

    public void debug(String msg) {
        if(debugInfo) System.out.println("D | " + getTime() + " " + id + " | " + msg);
    }

    public void logProtected(String msg) {
        if(protect) {
            return;
        }
        working(msg);
    }

    private String getTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(dateTimeFormatter);
    }
}
