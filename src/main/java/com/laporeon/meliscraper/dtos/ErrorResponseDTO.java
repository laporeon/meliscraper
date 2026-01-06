package com.laporeon.meliscraper.dtos;

import java.time.Instant;

public record ErrorResponseDTO(
        int status,
        String message,
        Instant timestamp
) {
}
