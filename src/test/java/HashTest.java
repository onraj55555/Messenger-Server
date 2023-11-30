import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.messenger.Encryption.Hasher;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class HashTest {
    Logger logger = LogManager.getLogger(HashTest.class);
    String str1 = "Hello world!";
    String str2 = "Hello world!";

    @Test
    public void testHashConsistency() {
        try {
            String hash1 = Hasher.hash(str1);
            String hash2 = Hasher.hash(str2);
            assertEquals(hash1, hash2);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
