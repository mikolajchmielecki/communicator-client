package hybrid;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    private RSA rsa = new RSA();

    @Test
    public void decryptionTest() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String message = "decrypteddMas";
        String encrypted = rsa.encrypt(message);
        assertEquals(message, rsa.decrypt(encrypted));
    }
}