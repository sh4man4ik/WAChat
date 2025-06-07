package com.wachat.WAChat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class UUID {

    private String userUUID;

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    @Override
    public String toString() {
        return userUUID;
    }
}
