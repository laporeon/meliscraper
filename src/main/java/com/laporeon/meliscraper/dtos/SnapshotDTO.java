package com.laporeon.meliscraper.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SnapshotDTO(UUID id, LocalDate snapshotDate, LocalDateTime createdAt, List<CategoryDTO> categories) {
}
