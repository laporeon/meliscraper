package com.laporeon.meliscraper.dtos;

import java.math.BigDecimal;

public record ProductDTO(
        Integer position,
        String name,
        String image,
        BigDecimal price,
        String link
) {
}
