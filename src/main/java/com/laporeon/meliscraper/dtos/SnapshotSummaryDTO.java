package com.laporeon.meliscraper.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record SnapshotSummaryDTO(UUID id, LocalDate snapshotDate) {
}
