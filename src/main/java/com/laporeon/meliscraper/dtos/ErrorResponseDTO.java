package com.laporeon.meliscraper.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        int status,
        String type,
        String message,
        Instant timestamp
) {
}
