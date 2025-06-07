package com.wachat.WAChat.controllers;

import com.wachat.WAChat.model.Messages;
import com.wachat.WAChat.repository.MessagesRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GetMessage {

    @Autowired
    MessagesRepository messagesRepository;

    List<Messages> messageList = new ArrayList<Messages>();
    ArrayList<String> messageArrayList = new ArrayList<String>();

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/getMessages")
    public ArrayList processData() {
        messageList = messagesRepository.findAll();
        if (messageList.size() > 0) {
            messageArrayList.clear();

            for (int i = 0; i < messageList.size(); i++) {
                Messages encryptedData = messageList.get(i);
                String decryptedData = encryptString(encryptedData.toString());

                messageArrayList.add(decryptedData);
            }
        }

        return messageArrayList;
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