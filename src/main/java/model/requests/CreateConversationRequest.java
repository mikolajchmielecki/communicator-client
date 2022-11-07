package model.requests;

import java.util.List;
import java.util.stream.Collectors;

public record CreateConversationRequest(String name, List<String> users) {

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
