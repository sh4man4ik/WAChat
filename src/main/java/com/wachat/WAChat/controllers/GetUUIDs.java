package com.wachat.WAChat.controllers;

import com.wachat.WAChat.model.Messages;
import com.wachat.WAChat.model.UUID;
import com.wachat.WAChat.repository.MessagesRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GetUUIDs {

    @Autowired
    MessagesRepository messagesRepository;

    List<UUID> uuidList = new ArrayList<UUID>();
    ArrayList<String> uuidArrayList = new ArrayList<String>();

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/getUUIDs")
    public ArrayList processData() {
        uuidList = messagesRepository.findAllUUIDs();

        if (uuidList.size() > 0) {
            uuidArrayList.clear();

            for (int i = 0; i < uuidList.size(); i++) {
                UUID encryptedData = uuidList.get(i);
                String decryptedData = encryptString(encryptedData.toString());

                uuidArrayList.add(decryptedData);
            }
        }

        return uuidArrayList;
    }

    private static String encryptString(String string) {
        Dotenv dotenv = Dotenv.load();
        String keyString = dotenv.get("SECRET_KEY");

        char[] key = keyString.toCharArray();

        StringBuilder output = new StringBuilder();

        for(int i = 0; i < string.length(); i++) {
            output.append((char) (string.charAt(i) ^ key[i % key.length]));
        }

        return output.toString();
    }
}