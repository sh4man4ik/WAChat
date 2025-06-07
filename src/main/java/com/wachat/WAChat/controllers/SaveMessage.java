package com.wachat.WAChat.controllers;

import com.wachat.WAChat.model.Messages;
import com.wachat.WAChat.repository.MessagesRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SaveMessage {

    @Autowired
    MessagesRepository messagesRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/send")
    public String processMessage(@RequestBody UserMessageDTO request) {
        String userUUID = encryptString(request.getUserUUID());
        String userMessage = encryptString(request.getMessage());

        messagesRepository.save(new Messages(userUUID, userMessage));

        return "index";
    }

    public static class UserMessageDTO {
        private String userUUID;
        private String message;

        public String getUserUUID() {
            return userUUID;
        }

        public void setUserUUID(String userUUID) {
            this.userUUID = userUUID;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
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