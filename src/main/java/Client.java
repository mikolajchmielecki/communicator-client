import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import model.Application;
import model.requests.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
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
    public static final String connectionMessage = "Hi!";
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {

        // generate client keys
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey clientPublicKey = kp.getPublic();
        PrivateKey clientPrivateKey = kp.getPrivate();
        String clientPublicKeyString = Base64.getEncoder().encodeToString(clientPublicKey.getEncoded());

        // connect to server
        Socket clientSocket = new Socket("127.0.0.1", 16123);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println(connectionMessage);
        int currentPort = Integer.parseInt(in.readLine());
        System.out.println("port =" + currentPort);
        clientSocket.close();

        // connection on new port
        Socket connectedSocket = new Socket("127.0.0.1", currentPort);
        out = new PrintWriter(connectedSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));

        // send user public key
        out.println(clientPublicKeyString);

        // get encoded server key
        String serverPublicKeyMessage = in.readLine();
        String decodedServerKey = Encryption.decryptLong(serverPublicKeyMessage, clientPrivateKey);
        LOGGER.info("SERVER PUBLIC KEY = " + decodedServerKey);

        // get server public key as object
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(decodedServerKey));
        PublicKey serverPublicKey = keyFactory.generatePublic(keySpecX509);

        // send control message
        String controlMessage = controlMessageGenerator(100);
        LOGGER.info("CONTROL MESSAGE = " + controlMessage);
        String encodedControlMessage = Encryption.encryptLong(controlMessage, serverPublicKey);
        LOGGER.info("ENCODED CONTROL MESSAGE = " + encodedControlMessage);
        out.println(encodedControlMessage);

        // get control message hash from server
        String controlHashFromServer = in.readLine();
        LOGGER.info("CONTROL HASH = " + controlHashFromServer);

        // decode hash
        String decodedHashFromServer = Encryption.decryptLong(controlHashFromServer, clientPrivateKey);
        LOGGER.info("ENCODED CONTROL HASH = " + decodedHashFromServer);

        // hash control message in client
        String controlHashTest = Hashing
                .sha256()
                .hashString(controlMessage + clientPublicKeyString, StandardCharsets.UTF_8)
                .toString();
        LOGGER.info("CLIENT CODED HASH = " + controlHashTest);

        // check is hash the same
        if (controlHashTest.equals(decodedHashFromServer)) {
            LOGGER.info("SERVER VERIFIED");
            out.println("{\"action\": \"login\", \"body\":{\"login\":\"login2\",\"password\":\"haslo2\"}}");
            String status = in.readLine();
            LOGGER.info("STATUS: " + status);


            Application app = new Application();
            app.run(kp, serverPublicKey, in, out);

        } else {
            LOGGER.info("SERVER IS NOT TRUSTED");
        }
    }

    private static String controlMessageGenerator(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

}
