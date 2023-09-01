package com.chat.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Configuration
public class StorageConfig {
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            PrintWriter writer = new PrintWriter("chat-history.txt", StandardCharsets.UTF_8);
            writer.println("=====CHAT BEGINNING=====");
            writer.close();
        };
    }
}
