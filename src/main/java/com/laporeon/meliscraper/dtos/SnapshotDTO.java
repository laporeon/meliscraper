package com.laporeon.meliscraper.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SnapshotDTO(
        UUID id,
        LocalDate snapshotDate,
        List<CategoryDTO> categories

) {
}
