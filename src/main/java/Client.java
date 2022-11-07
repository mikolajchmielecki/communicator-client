import com.google.common.hash.Hashing;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;


public class Client {

    private static final String HELLO = "Hi!";
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 16123;

    private String aesKey;
    private BufferedReader in;
    private PrintWriter out;

    public static void main(String[] args) throws Exception {

        Client client = new Client();
        client.handshake();

        Scanner scanner = new Scanner(System.in);
        Application app = new Application(scanner, client.aesKey, client.in, client.out);
        app.run();
        scanner.close();

    }

    private static String controlMessageGenerator(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    private static String generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey key = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private void handshake() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        PublicKey clientPublicKey = keyPair.getPublic();
        PrivateKey clientPrivateKey = keyPair.getPrivate();
        String clientPublicKeyEncoded = Base64.getEncoder().encodeToString(clientPublicKey.getEncoded());

        Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
        PrintWriter portInitOut = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader portInitIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        portInitOut.println(HELLO);
        int customPort = Integer.parseInt(portInitIn.readLine());
        clientSocket.close();

        Thread.sleep(100);
        Socket connectedSocket = new Socket(SERVER_IP, customPort);
        this.out = new PrintWriter(connectedSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));

        out.println(clientPublicKeyEncoded);

        String serverPublicKeyEncrypted = in.readLine();
        String decryptedServerPublicKey = Encryption.decryptLong(serverPublicKeyEncrypted, clientPrivateKey);
        PublicKey serverPublicKey = decryptPublicKey(decryptedServerPublicKey);
        String controlMessage = controlMessageGenerator(100);
        String encodedControlMessage = Encryption.encryptLong(controlMessage, serverPublicKey);
        out.println(encodedControlMessage);

        String controlHashFromServer = in.readLine();

        String decodedHashFromServer = Encryption.decryptLong(controlHashFromServer, clientPrivateKey);

        String controlHashTest = Hashing.sha256().hashString(controlMessage + clientPublicKeyEncoded, StandardCharsets.UTF_8).toString();
        if (!controlHashTest.equals(decodedHashFromServer)) {
            throw new RuntimeException("Faked server");
        }
        this.aesKey = generateAESKey();
        out.println(Encryption.encryptLong(aesKey, serverPublicKey));
    }

    private PublicKey decryptPublicKey(String decryptedServerPublicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(decryptedServerPublicKey));
        return keyFactory.generatePublic(keySpecX509);
    }

}
