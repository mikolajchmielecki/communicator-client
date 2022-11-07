package model.responses;

import model.Message;

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
            result.put(o.get("name").getAsString(), o.get("names").getAsJsonArray().asList().stream().map(e -> e.getAsString()).collect(Collectors.toList()));
        });
        return result;
    }

    @Override
    public String toString() {
        if (getStatus() == 200) {
            Map<String, List<String>> conversations = getConversations();

            StringBuilder stringBuilder = new StringBuilder("");

            conversations.keySet().forEach(k -> {
                stringBuilder.append("Nazwa konwersacji:" + k + "\nUżytkownicy:\n");
                conversations.get(k).forEach(u -> {
                    stringBuilder.append(u + "; ");
                });
                stringBuilder.append("\n\n");
            });
            return stringBuilder.toString();
        } else {
            return getResponse();
        }
    }
}
