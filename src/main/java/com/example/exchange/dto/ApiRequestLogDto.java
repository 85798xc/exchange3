package com.example.exchange.dto;

import java.time.Instant;

public record ApiRequestLogDto(
    String response,
    String url,
    Instant timestamp
) {
}
