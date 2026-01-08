package com.laporeon.meliscraper.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDTO(
        Integer position,
        String name,
        String image,
        BigDecimal price,
        String link,
        LocalDate snapshotDate
) {
}