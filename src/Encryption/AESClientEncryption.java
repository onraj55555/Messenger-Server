package Encryption;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESClientEncryption {
    private static byte[] generateRandomIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16]; // 16 bytes for AES-128
        random.nextBytes(iv);
        return iv;
    }
    public static String encryptAES(String plaintext, String password, String ivString) throws Exception {
        byte[] decodedPassword = Base64.getDecoder().decode(password);
        SecretKeySpec secretKey = new SecretKeySpec(decodedPassword, "AES");

        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] msgBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        byte[] cipherBytes = cipher.doFinal(msgBytes);

        return Base64.getEncoder().encodeToString(cipherBytes);
    }
}
