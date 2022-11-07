package model.requests;

public record GetConversationMessagesRequest(String conversation) {

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
