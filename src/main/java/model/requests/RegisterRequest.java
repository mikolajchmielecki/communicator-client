package model.requests;

public record RegisterRequest(String login, String password) {

    @Override
    public String toString() {
        return "{\"action\": " +
                "\"register\", " +
                "\"body\": { " +
                "\"login\": \"" + login + "\", " +
                "\"password\": " + password + "\"" +
                "}" +
                "}";
    }
}