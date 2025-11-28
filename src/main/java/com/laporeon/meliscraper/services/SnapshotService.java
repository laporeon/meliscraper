package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.helpers.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final Scraper scraper;

    public SnapshotDTO getSnapshot() {
        List<CategoryDTO> categories = scraper.scrapeCategories();

        return new SnapshotDTO(
                UUID.randomUUID(),
                LocalDate.now(),
                LocalDateTime.now(), categories);
    }

}
