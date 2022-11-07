package model.requests;

import java.util.Scanner;

public record MessageRequest(String conversation, String content) {

    public static MessageRequest create(Scanner scanner){
        System.out.println("Podaj nazwę konwersacji");
        String conversation = scanner.nextLine();
        System.out.println("Podaj treść wiadomości");
        String content = scanner.nextLine();

        return new MessageRequest(conversation, content);
    }

    @Override
    public String toString() {
        return "{\"action\": " +
                "\"message\", " +
                "\"body\": { " +
                "\"conversation\": \"" + conversation + "\", " +
                "\"content\": " + content + "\"" +
                "}" +
                "}";
    }

}
