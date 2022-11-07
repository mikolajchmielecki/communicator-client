package model.requests;

import java.util.Scanner;

public record GetConversationMessagesRequest(String conversation) {

    public static GetConversationMessagesRequest create(Scanner scanner){
        System.out.println("Podaj nazwę konwersacji");
        String conversation = scanner.nextLine();

        return new GetConversationMessagesRequest(conversation);
    }

    @Override
    public String toString() {
        return "{\"action\": " +
                    "\"getConversationMessages\", " +
                    "\"body\": { " +
                        "\"conversation\": \"" + conversation + "\"" +
                    "}" +
                "}";
    }
}
