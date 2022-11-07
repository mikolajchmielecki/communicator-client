package model.requests;

import java.util.Scanner;

public record RegisterRequest(String login, String password) {

    public static RegisterRequest create(Scanner scanner){
        System.out.println("Podaj login");
        String login = scanner.nextLine();
        System.out.println("Podaj hasło");
        String password = scanner.nextLine();

        return new RegisterRequest(login, password);
    }
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