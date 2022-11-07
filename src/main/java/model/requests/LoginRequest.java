package model.requests;

public record LoginRequest(String login, String password) {

    @Override
    public String toString() {
        return "{\"action\": " +
                "\"login\", " +
                "\"body\": { " +
                "\"login\": \"" + login + "\", " +
                "\"password\": " + password + "\"" +
                "}" +
                "}";
    }
}
