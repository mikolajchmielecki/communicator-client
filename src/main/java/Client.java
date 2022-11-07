import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;


public class Client {

    private static final String connectionMessage = "Hi!";
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 16123;
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {

        // generate client keys
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey clientPublicKey = kp.getPublic();
        PrivateKey clientPrivateKey = kp.getPrivate();
        String clientPublicKeyString = Base64.getEncoder().encodeToString(clientPublicKey.getEncoded());

        // connect to servers
        Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println(connectionMessage);
        int currentPort = Integer.parseInt(in.readLine());
        clientSocket.close();

        // connection on new port
        Socket connectedSocket = new Socket(SERVER_IP, currentPort);
        out = new PrintWriter(connectedSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));

        // send user public key
        out.println(clientPublicKeyString);

        String serverPublicKeyMessage = in.readLine();
        String decodedServerKey = Encryption.decryptLong(serverPublicKeyMessage, clientPrivateKey);
        log.debug("SERVER PUBLIC KEY = " + decodedServerKey);

        // get server public key as object
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(decodedServerKey));
        PublicKey serverPublicKey = keyFactory.generatePublic(keySpecX509);

        // send control message
        String controlMessage = controlMessageGenerator(100);
        log.debug("CONTROL MESSAGE = " + controlMessage);
        String encodedControlMessage = Encryption.encryptLong(controlMessage, serverPublicKey);
        log.debug("ENCODED CONTROL MESSAGE = " + encodedControlMessage);
        out.println(encodedControlMessage);

        // get control message hash from server
        String controlHashFromServer = in.readLine();
        log.debug("CONTROL HASH = " + controlHashFromServer);

        // decode hash
        String decodedHashFromServer = Encryption.decryptLong(controlHashFromServer, clientPrivateKey);
        log.debug("ENCODED CONTROL HASH = " + decodedHashFromServer);

        // hash control message in client
        String controlHashTest = Hashing
                .sha256()
                .hashString(controlMessage + clientPublicKeyString, StandardCharsets.UTF_8)
                .toString();
        log.debug("CLIENT CODED HASH = " + controlHashTest);

        // check is hash the same
        if (controlHashTest.equals(decodedHashFromServer)) {
            String aesKey = generateAESKey();
            out.println(Encryption.encryptLong(aesKey, serverPublicKey));


            Scanner scanner = new Scanner(System.in);
            Application app = new Application(scanner, aesKey, in, out);
            app.run();
            scanner.close();

        } else {
            log.debug("SERVER IS NOT TRUSTED");
        }
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

}
