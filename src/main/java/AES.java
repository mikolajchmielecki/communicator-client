import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {

    public static String encrypt(String data, String key) throws Exception {
        Cipher encryptionCipher = Cipher.getInstance("AES");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKeyFromString(key));
        byte[] encryptedMessageBytes =
                encryptionCipher.doFinal(data.getBytes());
        String encryptedMessage =
                Base64.getEncoder().encodeToString(encryptedMessageBytes);
        return encryptedMessage;
    }

    public static String decrypt(String encryptedData, String key) throws Exception {
        Cipher decryptionCipher = Cipher.getInstance("AES");
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKeyFromString(key));
        byte[] decryptedMessageBytes =
                decryptionCipher.doFinal(Base64.getDecoder().decode(encryptedData));
        String decryptedMessage = new String(decryptedMessageBytes);
        return decryptedMessage;
    }

    private static SecretKey secretKeyFromString(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }


}
