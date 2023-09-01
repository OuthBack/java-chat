package com.chat.app.services;

import com.chat.app.repositories.ChatRepository;
import com.chat.app.dtos.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public Find getMessagePaginated(Integer page, Integer offset){
        try {
            return chatRepository.find((page - 1) * offset, offset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void handleMessage(String message){
        try {
            chatRepository.save(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
