package model.responses;

import model.Message;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
            LocalDateTime dateTime = LocalDateTime.parse(e.get("dateTime").getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return new Message(author, content, dateTime);
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        if (getStatus() == 200) {
            List<Message> messages = getConversationMessages();
            StringBuilder stringBuilder = new StringBuilder("");
            messages.forEach(m -> {
                stringBuilder.append("Nadawca: " + m.author() + "\nData: " + m.dateTime() + "\nZawartość: \n" + m.content() + "\n\n");
            });

            return stringBuilder.toString();
        } else {
            return getResponse();
        }
    }

}
