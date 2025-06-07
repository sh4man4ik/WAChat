package com.wachat.WAChat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Messages")
public class Messages {

    @Id
    private String id;

    private String userUUID;
    private String message;

    public Messages(String userUUID, String message) {
        super();
        this.userUUID = userUUID;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
