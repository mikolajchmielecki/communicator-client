package hybrid;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Hybrid {

    private RSA rsa;

    public Hybrid() {
        this.rsa = new RSA();
    }

    public String encrypt(String message) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey key = keyGenerator.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            String encryptedAESKey = rsa.encrypt(encodedKey);


            String encryptedMessage = AES.encrypt(message, encodedKey);
            return encryptedAESKey + "\n" + encryptedMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String message) {
        try {
            String aesKey = rsa.decrypt(message.split("\n")[0]);


            String messageBody = AES.decrypt(message.split("\n")[1], aesKey);
            return messageBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
