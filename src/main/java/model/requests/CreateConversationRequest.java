package model.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public record CreateConversationRequest(String name, List<String> users) {

    public static CreateConversationRequest create(Scanner scanner){
        System.out.println("Podaj nazwę konwersacji:");
        String name = scanner.nextLine();

        System.out.println("Wprowadź nazwy użytkowników:");
        List<String> list = new ArrayList<>();
        String user;
        while(true){
            user = scanner.nextLine();
            if(!user.isEmpty()){
                list.add(user);
            } else {
                break;
            }

        }

        return new CreateConversationRequest(name, list);
    }

    @Override
    public String toString() {
        return "{\"action\": " +
                "\"createConversation\", " +
                "\"body\": { " +
                    "\"name\": \"" + name + "\", " +
                    "\"users\": " + users.stream().collect(Collectors.joining("\",\"", "[\"", "\"]")) +
                "}" +
                "}";
    }
}
