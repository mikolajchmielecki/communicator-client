package model.requests;

public record GetConversationMessages(String conversation) {

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
