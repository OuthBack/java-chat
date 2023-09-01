package com.chat.app.controllers;

import com.chat.app.annotations.ControllerWebsocket;
import com.chat.app.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.*;

import java.util.ArrayList;
import java.util.List;

@ControllerWebsocket("/chat")
public class ChatController implements WebSocketHandler {
    private final ChatService chatService;
    private List<WebSocketSession> clients = new ArrayList<>();
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.add(session);
        int page = 1;

        while(true){
            var message = chatService.getMessagePaginated(page, 1);
            for(String line : message.lines()){
                session.sendMessage(new TextMessage(line));
            }

            if(!message.hasMore()){
                break;
            }

            page++;
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        for(var client : clients){
            if(!client.equals(session)){
                client.sendMessage(message);
            }

            chatService.handleMessage(message.getPayload().toString());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
