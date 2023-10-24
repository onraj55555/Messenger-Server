package Http;

import Encryption.ServerEncryption;
import Http.ServerConnectionHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.ArrayList;

public class HttpThread extends Thread {
    HttpServer server;
    ArrayList<String> contexts; // Voor meer contexts?

    ServerEncryption serverEncryption;

    public HttpThread(HttpServer server) {
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
        System.out.println("Server started");
    }
}
