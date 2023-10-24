import Encryption.ServerEncryption;
import Http.HttpExchangePool;
import Http.HttpThread;
import User.TokenGenerator;
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
        ServerEncryption.getInstance(); // Generating keys at startup
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
                System.out.println("Token: " + TokenGenerator.testToken());
            }
            if(serverCommand.equals("fstop")) {
                HttpExchangePool.closeAllOpenExchanges();
            }
            if(serverCommand.equals("key")) {
                System.out.println(ServerEncryption.getInstance());
            }
            if(serverCommand.equals("keys")) {
                String publicKey = ServerEncryption.getInstance().getPublicKeyString();
                String privateKey = ServerEncryption.getInstance().getPrivateKeyString();

                System.out.println();
                System.out.println(publicKey);
                System.out.println();
                System.out.println(privateKey);
                System.out.println();
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