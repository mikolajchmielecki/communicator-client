import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    private RSA rsa = new RSA();

    RSATest() throws NoSuchAlgorithmException {
    }

    @Test
    public void decryptionTest() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        String message = "decrypteddMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasageddecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecryptedMasagedecr";
        String encrypted = rsa.encrypt(message);
        assertEquals(message, rsa.decrypt(encrypted));
    }
}