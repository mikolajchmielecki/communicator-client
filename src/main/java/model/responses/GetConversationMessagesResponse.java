package model.responses;

import model.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetConversationMessagesResponse extends ResponseAbstract {

    public GetConversationMessagesResponse(String response) {
        super(response);
    }

    public List<Message> getConversationMessages() {
       return getBody().get("messages").getAsJsonArray().asList().stream().map(e -> e.getAsJsonObject()).map((e) -> {
            String author = e.get("author").getAsString();
            String content = e.get("content").getAsString();
            LocalDateTime dateTime = LocalDateTime.parse(e.get("dateTime").getAsString());
            return new Message(author, content, dateTime);
        }).collect(Collectors.toList());
    }

}
