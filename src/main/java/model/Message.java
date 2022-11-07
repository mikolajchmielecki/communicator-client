package model;

import java.time.LocalDateTime;

public record Message(String author, String content, LocalDateTime dateTime) {
}
