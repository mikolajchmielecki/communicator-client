package model.requests;

public record MessageRequest(String conversation, String content) {

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
