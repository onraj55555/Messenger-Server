package org.messenger.Http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.messenger.Encryption.ServerEncryption;
import org.messenger.Http.ServerConnectionHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.ArrayList;

public class HttpThread extends Thread {
    private final Logger logger;
    HttpServer server;
    ArrayList<String> contexts; // Voor meer contexts?

    public HttpThread(HttpServer server) {
        logger = LogManager.getLogger(HttpThread.class);
        this.server = server;
        //HttpHandler serverConnectionHandler = new ServerConnectionHandler();
        server.createContext("/login", new ServerLoginHandler());
        server.createContext("/getConnection", new ServerConnectionHandler());
        server.createContext("/test", new TestHandler());
        server.createContext("/testMisc", new TestMiscHandler());
    }

    @Override
    public void run() {
        server.start();
        logger.info("Server started");
    }
}
