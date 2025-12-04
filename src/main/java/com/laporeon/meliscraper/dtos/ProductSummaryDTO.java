package com.laporeon.meliscraper.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductSummaryDTO(
        String name,
        BigDecimal price,
        String link,
        LocalDate snapshotDate
) {
}
