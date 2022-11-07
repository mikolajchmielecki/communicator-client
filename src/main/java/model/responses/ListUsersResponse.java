package model.responses;

import java.util.List;
import java.util.stream.Collectors;

public class ListUsersResponse extends ResponseAbstract {


    public ListUsersResponse(String response) {
        super(response);
    }

    public List<String> getUsers() {
        return getBody().get("users").getAsJsonArray().asList().stream().map(e -> e.getAsString()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        List<String> users = getUsers();

        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("Użytkownicy:\n");

        users.forEach(u -> {
            stringBuilder.append(u + "; ");
        });
        stringBuilder.append("\n\n");

        return stringBuilder.toString();
    }

}
