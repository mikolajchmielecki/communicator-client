package model.responses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListConversationsResponse extends ResponseAbstract {

    public ListConversationsResponse(String response) {
        super(response);
    }

    public Map<String, List<String>> getConversations() {
        Map<String, List<String>> result = new HashMap<>();
        getBody().get("conversations").getAsJsonArray().asList().stream().map(e -> e.getAsJsonObject()).forEach((o) -> {
            result.put(o.get("name").getAsString(), o.get("users").getAsJsonArray().asList().stream().map(e -> e.getAsString()).collect(Collectors.toList()));
        });
        return result;
    }


}
