package com.chat.app.repositories;

import com.chat.app.dtos.Find;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Repository
public class ChatRepository {
    String file = "chat-history.txt";
    public void save(String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.newLine();
        writer.write(data);
        writer.close();
    }

    public Long count() throws IOException {
        Stream<String> lines = Files.lines(Paths.get(file));
        return lines.count();
    }

    public Find find(Integer skip, Integer limit) throws IOException {
        Stream<String> lines = Files.lines(Paths.get(file));
        Long count = this.count();
        return new Find(lines.skip(skip).limit(limit).toList(), count, (skip + limit <= count));
    }
}
