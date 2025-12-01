package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.entities.Snapshot;
import com.laporeon.meliscraper.helpers.Scraper;
import com.laporeon.meliscraper.repositories.CategoryRepository;
import com.laporeon.meliscraper.repositories.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final CategoryRepository categoryRepository;

    public SnapshotDTO getSnapshot() {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(LocalDate.now())
                                              .orElseGet(() -> snapshotRepository.save(Snapshot.builder().build()));

        List<Category> categories = Scraper.scrapeCategories()
                                           .stream()
                                           .map(this::findOrCreateCategory)
                                           .collect(Collectors.toList());

        if (snapshot.getCategories() == null || snapshot.getCategories().isEmpty()) {
            snapshot.setCategories(categories);
            snapshotRepository.save(snapshot);
        }

        List<CategoryDTO> categoryDTOs = categories.stream()
                                                   .map(cat -> new CategoryDTO(
                                                           cat.getName(),
                                                           cat.getSlug()))
                                                   .collect(Collectors.toList());

        return new SnapshotDTO(snapshot.getId(),
                               snapshot.getSnapshotDate(), snapshot.getCreatedAt(),
                               categoryDTOs);
    }

    private Category findOrCreateCategory(Map<String, String> category) {
        return categoryRepository.findBySlug(category.get("slug"))
                                 .orElseGet(() -> categoryRepository.save(Category.builder()
                                                                                  .name(category.get("title"))
                                                                                  .slug(category.get("slug"))
                                                                                  .build()));
    }

}
