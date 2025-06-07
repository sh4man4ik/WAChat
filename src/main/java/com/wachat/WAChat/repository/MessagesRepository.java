package com.wachat.WAChat.repository;

import com.wachat.WAChat.model.Messages;
import com.wachat.WAChat.model.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessagesRepository extends MongoRepository<Messages, String> {
    @Query(value = "{}", fields = "{'userUUID' : 1, '_id': 0}")
    List<UUID> findAllUUIDs();
}
