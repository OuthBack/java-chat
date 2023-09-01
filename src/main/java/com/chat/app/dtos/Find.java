package com.chat.app.dtos;

import java.util.List;

public record Find(List<String> lines, Long count, boolean hasMore) {
}
