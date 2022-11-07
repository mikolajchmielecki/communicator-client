package hybrid;

import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

    private AES aes = new AES();

    @Test
    public void aesTest() throws Exception {
        String message = "adfgagadfasf1234";
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey key = keyGenerator.generateKey();
        String encodedKey =  Base64.getEncoder().encodeToString(key.getEncoded());
        String encrypted = aes.encrypt(message, encodedKey);
        String decrypted = aes.decrypt(encrypted, encodedKey);
        assertEquals(message, decrypted);
    }

}