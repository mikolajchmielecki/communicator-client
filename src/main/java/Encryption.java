import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

public class Encryption {

    public static final int ENCODE_WINDOW = 64;
    public static final int DECODE_WINDOW = 172;

    public static String encryptLong(String message, Key publicKey) throws Exception {
        var bytes = message.getBytes(StandardCharsets.UTF_8);
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (message.getBytes().length > ENCODE_WINDOW * (index + 1)) {
            var toEncrypt = Arrays.copyOfRange(bytes, index * ENCODE_WINDOW, (index + 1) * ENCODE_WINDOW);
            result.append(encryptText(toEncrypt, publicKey));
            index++;
        }
        result.append(encryptText(Arrays.copyOfRange(bytes, ENCODE_WINDOW * (index), bytes.length), publicKey));
        return result.toString();
    }

    public static String decryptLong(String message, Key privateKey) throws Exception {
        var bytes = message.getBytes(StandardCharsets.UTF_8);
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (bytes.length > DECODE_WINDOW * i + 1) {
            var toDecrypt = Arrays.copyOfRange(bytes, i * DECODE_WINDOW, (i + 1) * DECODE_WINDOW);
            result.append(decryptText(toDecrypt, privateKey));
            i++;
        }
        return result.toString();
    }

    private static String encryptText(byte[] contentBytes, Key pubKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(contentBytes));
    }

    private static String decryptText(byte[] cipherContent, Key privKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherContent)));
    }
}
