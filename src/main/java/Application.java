import hybrid.AES;
import model.requests.*;
import model.responses.GetMessagesResponse;
import model.responses.ListConversationsResponse;
import model.responses.ListUsersResponse;
import model.responses.ResponseAbstract;
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
            System.out.println("Wybierz pozycję menu: \n1. Rejestracja\n2. Logowanie\n3. Wyślij wiadomość\n4. Wyświetl listę użytkowników\n5. Wyświetl listę konwersjacji\n6. Nowa konwersjacja\n7. Nowe wiadomości\n8. Wiadomości z konwersacji\n9. Koniec");
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

    private void register() throws IOException {
        RegisterRequest request = RegisterRequest.create(scanner);
        out.println(request);
        String response = in.readLine();
        ResponseAbstract responseObject = new ResponseAbstract(response);
        System.out.println(response);
    }

    private void login() throws IOException {
        LoginRequest request = LoginRequest.create(scanner);
        out.println(request);
        String response = in.readLine();
        ResponseAbstract responseObject = new ResponseAbstract(response);
        System.out.println(response);
    }

    private void sendMessage() throws IOException {
        MessageRequest request = MessageRequest.create(scanner);
        out.println(request);
        String response = in.readLine();
        ResponseAbstract responseObject = new ResponseAbstract(response);
        System.out.println(response);
    }

    private void listUsers() throws IOException {
        ListUsersRequest request = new ListUsersRequest();
        out.println(request);
        String response = in.readLine();
        ListUsersResponse listUsersResponse = new ListUsersResponse(response);
        System.out.println(listUsersResponse);
    }

    private void listConversations() throws IOException {
        ListConversationsRequest request = new ListConversationsRequest();
        out.println(request);
        String response = in.readLine();
        ListConversationsResponse listConversationsResponse = new ListConversationsResponse(response);
        System.out.println(listConversationsResponse);
    }

    private void newConversation() throws IOException {
        CreateConversationRequest request = CreateConversationRequest.create(scanner);
        out.println(request);
        String response = in.readLine();
        ResponseAbstract newConversationResponse = new ResponseAbstract(response);
        System.out.println(response);
    }

    private void newMessages() throws IOException {
        GetMessagesRequest request = new GetMessagesRequest();
        out.println(request);
        String response = in.readLine();
        GetMessagesResponse getMessagesResponse = new GetMessagesResponse(response);
        System.out.println(getMessagesResponse);
    }

    private void messagesForConversation() throws IOException {
        GetConversationMessagesRequest request = GetConversationMessagesRequest.create(scanner);
        out.println(request);
        String response = in.readLine();
        ListConversationsResponse listConversationsResponse = new ListConversationsResponse(response);
        System.out.println(listConversationsResponse);
    }

    private String sendAndReceive(String message) throws Exception {
        String encrypted = AES.encrypt(message, aesKey);
        out.println(encrypted);
        return AES.decrypt(in.readLine(), aesKey);
    }

}
