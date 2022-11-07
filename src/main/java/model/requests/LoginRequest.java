package model.requests;

import java.util.Scanner;

public record LoginRequest(String login, String password) {

    public static LoginRequest create(Scanner scanner){
        System.out.println("Podaj login");
        String login = scanner.nextLine();
        System.out.println("Podaj hasło");
        String password = scanner.nextLine();

        return new LoginRequest(login, password);
    }

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
