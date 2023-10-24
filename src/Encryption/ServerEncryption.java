package Encryption;

import Debug.ServerLogger;
import Errors.InvalidAcceptType;
import Http.HTTP;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ServerEncryption {
    private static ServerEncryption singleton;
    private String algorythm = "RSA";
    private final KeyPair keyPair;
    private final int keysize = 4096;

    private static final ServerLogger serverLogger = new ServerLogger(ServerEncryption.class.getName());

    private ServerEncryption(String algorithm) throws NoSuchAlgorithmException {
        serverLogger.working("Generating instance");
        KeyPairGenerator keyPairGenerator =  KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keysize);
        serverLogger.working("Generating key pair");
        keyPair = keyPairGenerator.generateKeyPair();
        serverLogger.working("Keypair generated");
    }

    public static ServerEncryption getInstance(String algorythm) throws NoSuchAlgorithmException {
        serverLogger.working("Requested encryption instance");
        if(singleton == null) {
            serverLogger.working("New instance created");
            singleton = new ServerEncryption(algorythm);
            return singleton;
        }
        return singleton;
    }

    public int getKeysize() {
        return keysize;
    }

    public static ServerEncryption getInstance() throws NoSuchAlgorithmException {
        return getInstance("RSA");
    }

    public String encode(String decryptedString) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorythm);
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encryptedBytes = cipher.doFinal(decryptedString.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decode(String encryptedString) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(algorythm);
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
        return new String(decryptedBytes);
    }

    public String encodeExtern(String decryptedString, String publicKeyString) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorythm);
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorythm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(decryptedString.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public String getPublicKeyString() {
        serverLogger.working("Public key requested");
        return encodePublicKey();
    }

    public String getPrivateKeyString() {
        serverLogger.working("Private key requested");
        return encodePrivateKey();
    }

    private String encodePublicKey() {
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    private String encodePrivateKey() {
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    public String toString(String type) {
        if(type.equals(HTTP.ACCEPT.TEXT)) {
            return getPublicKeyString();
        }
        if(type.equals(HTTP.ACCEPT.JSON)) {
            return "{\"publicKey\":\"" + getPublicKeyString() + "\"}";
        }

        throw new InvalidAcceptType(type);
    }

    @Override
    public String toString() {
        return toString("text/plain");
    }
}
