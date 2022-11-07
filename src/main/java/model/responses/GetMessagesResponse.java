package model.responses;

import model.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetMessagesResponse extends ResponseAbstract {

    public GetMessagesResponse(String response) {
        super(response);
    }

    public Map<String, List<Message>> getMessageUpdatedMessages() {
        Map<String, List<Message>> result = new HashMap<>();
        getBody().get("updatedConversations").getAsJsonArray().asList().stream().map(e -> e.getAsJsonObject()).forEach((o) -> {
            result.put(o.get("conversation").getAsString(), o.get("messages").getAsJsonArray().asList().stream().map(e -> e.getAsJsonObject()).map((e) -> {
                String author = e.get("author").getAsString();
                String content = e.get("content").getAsString();
                LocalDateTime dateTime = LocalDateTime.parse(e.get("dateTime").getAsString());
                return new Message(author, content, dateTime);
            }).collect(Collectors.toList()));
        });
        return result;
    }




}
