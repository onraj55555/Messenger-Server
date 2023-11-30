package org.messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.messenger.Encryption.ServerEncryption;
import org.messenger.Http.HttpExchangePool;
import org.messenger.Http.HttpThread;
import org.messenger.User.TokenGenerator;
import com.sun.net.httpserver.HttpServer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Logger logger = LogManager.getLogger(Main.class);
        //ServerEncryption.getInstance(); // Generating keys at startup
        //String url = "192.168.0.242";
        String url = "localhost";
        HttpServer server = HttpServer.create(new InetSocketAddress(url, 8001), 0);
        Thread httpThread = new HttpThread(server);
        httpThread.start();
        String serverCommand;
        Scanner sc = new Scanner(System.in);
        serverCommand = sc.nextLine().strip();
        while(!serverCommand.equals("stop")) {
            if(serverCommand.equals("token")) {
                logger.info("Token: " + TokenGenerator.testToken());
            }
            if(serverCommand.equals("fstop")) {
                HttpExchangePool.closeAllOpenExchanges();
            }
            if(serverCommand.equals("key")) {
                logger.info(ServerEncryption.getInstance());
            }
            if(serverCommand.equals("keys")) {
                String publicKey = ServerEncryption.getInstance().getPublicKeyString();
                String privateKey = ServerEncryption.getInstance().getPrivateKeyString();

                logger.info(publicKey);
                logger.info(privateKey);
            }

            if(serverCommand.equals("clear")) {
                System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }
            serverCommand = sc.nextLine();
        }
        server.stop(0);
        httpThread.join();
    }
}