package com.chat.app.config;

import com.chat.app.annotations.ControllerWebsocket;
import com.chat.app.services.ChatService;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatService chatService;
    public WebSocketConfig(ChatService chatService) {
        this.chatService = chatService;

    }
    private HashMap<String, WebSocketHandler> getAllUrls(){
        HashMap<String, WebSocketHandler> urlList = new HashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages("com.chat.app"));
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ControllerWebsocket.class);

        for (Class<?> controller : annotated) {
            ControllerWebsocket request = controller.getAnnotation(ControllerWebsocket.class);
            String url = request.value();
            try {
                urlList.put(url, (WebSocketHandler) controller.getDeclaredConstructor(chatService.getClass()).newInstance(chatService));
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return urlList;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        for(var url: getAllUrls().entrySet()){
            registry.addHandler(url.getValue(), url.getKey());
        }
    }

}
