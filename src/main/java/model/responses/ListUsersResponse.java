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

}
