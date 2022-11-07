import hybrid.AES;
import model.requests.*;
import model.responses.*;
import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Scanner;

public class Application {

    private Scanner scanner;
    private String aesKey;
    private BufferedReader in;
    private PrintWriter out;

    public Application(Scanner scanner, String aesKey, BufferedReader in, PrintWriter out) {
        this.scanner = scanner;
        this.aesKey = aesKey;
        this.in = in;
        this.out = out;
    }

    public void run() {
        while(true) {
            System.out.println("Wybierz pozycję menu: \n1. Rejestracja\n2. Logowanie\n3. Wyślij wiadomość\n4. Wyświetl listę użytkowników\n5. Wyświetl listę konwersacji\n6. Nowa konwersacja\n7. Nowe wiadomości\n8. Wiadomości z konwersacji\n9. Koniec");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            try {
                switch (input) {
                    case "1":
                        register();
                        break;
                    case "2":
                        login();
                        break;
                    case "3":
                        sendMessage();
                        break;
                    case "4":
                        listUsers();
                        break;
                    case "5":
                        listConversations();
                        break;
                    case "6":
                        newConversation();
                        break;
                    case "7":
                        newMessages();
                        break;
                    case "8":
                        messagesForConversation();
                        break;
                    case "9":
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void register() throws Exception {
        RegisterRequest request = RegisterRequest.create(scanner);
        String response = sendAndReceive(request.toString());
        ResponseAbstract responseObject = new ResponseAbstract(response);
        System.out.println(responseObject);
    }

    private void login() throws Exception {
        LoginRequest request = LoginRequest.create(scanner);
        String response = sendAndReceive(request.toString());
        ResponseAbstract responseObject = new ResponseAbstract(response);
        System.out.println(responseObject);
    }

    private void sendMessage() throws Exception {
        MessageRequest request = MessageRequest.create(scanner);
        String response = sendAndReceive(request.toString());
        ResponseAbstract responseObject = new ResponseAbstract(response);
        System.out.println(responseObject);
    }

    private void listUsers() throws Exception {
        ListUsersRequest request = new ListUsersRequest();
        String response = sendAndReceive(request.toString());
        ListUsersResponse listUsersResponse = new ListUsersResponse(response);
        System.out.println(listUsersResponse);
    }

    private void listConversations() throws Exception {
        ListConversationsRequest request = new ListConversationsRequest();
        String response = sendAndReceive(request.toString());
        ListConversationsResponse listConversationsResponse = new ListConversationsResponse(response);
        System.out.println(listConversationsResponse);
    }

    private void newConversation() throws Exception {
        CreateConversationRequest request = CreateConversationRequest.create(scanner);
        String response = sendAndReceive(request.toString());
        ResponseAbstract newConversationResponse = new ResponseAbstract(response);
        System.out.println(newConversationResponse);
    }

    private void newMessages() throws Exception {
        GetMessagesRequest request = new GetMessagesRequest();
        String response = sendAndReceive(request.toString());
        GetMessagesResponse getMessagesResponse = new GetMessagesResponse(response);
        System.out.println(getMessagesResponse);
    }

    private void messagesForConversation() throws Exception {
        GetConversationMessagesRequest request = GetConversationMessagesRequest.create(scanner);
        String response = sendAndReceive(request.toString());
        GetConversationMessagesResponse getConversationMessagesResponse = new GetConversationMessagesResponse(response);
        System.out.println(getConversationMessagesResponse);
    }

    private String sendAndReceive(String message) throws Exception {
        String encrypted = AES.encrypt(message, aesKey);
        out.println(encrypted);
        return AES.decrypt(in.readLine(), aesKey);
    }

}
