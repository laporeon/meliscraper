package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.ProductDTO;
import com.laporeon.meliscraper.dtos.SnapshotSummaryDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import com.laporeon.meliscraper.exceptions.ResourceNotFoundException;
import com.laporeon.meliscraper.helpers.Scraper;
import com.laporeon.meliscraper.repositories.CategoryRepository;
import com.laporeon.meliscraper.repositories.ProductRepository;
import com.laporeon.meliscraper.repositories.SnapshotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private static final String NOT_FOUND_MESSAGE = "No snapshots found for date '%s'";

    private final SnapshotRepository snapshotRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Map<String, List<SnapshotSummaryDTO>> getSnapshots() {
        return Map.of("snapshots", snapshotRepository.findAll()
                                 .stream()
                                 .map(s -> new SnapshotSummaryDTO(s.getId(), s.getSnapshotDate()))
                                 .toList());
    }

    @Transactional
    public SnapshotDTO getDailySnapshot() {
        Optional<Snapshot> existingSnapshot = snapshotRepository.findBySnapshotDate(LocalDate.now());

        if (existingSnapshot.isPresent()) {
            return buildSnapshotDTO(existingSnapshot.get());
        }

        List<CategoryDTO> categoriesDTOList = Scraper.scrape();

        Snapshot snapshot = snapshotRepository.save(
                Snapshot.builder()
                        .build());

        categoriesDTOList.forEach(cat -> findOrCreateCategoryWithProducts(snapshot, cat));

        return buildSnapshotDTO(snapshotRepository.findBySnapshotDate(LocalDate.now()).orElseThrow());
    }

    public SnapshotDTO findByDate(LocalDate date) {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(date)
                                              .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE.formatted(date)));

        return buildSnapshotDTO(snapshot);
    }

    private void findOrCreateCategoryWithProducts(Snapshot snapshot, CategoryDTO dto) {
        Category category = categoryRepository.findBySlug(dto.slug())
                                              .orElseGet(() -> categoryRepository.save(
                                                      Category.builder()
                                                              .name(dto.name())
                                                              .slug(dto.slug())
                                                              .build()));

        dto.products().forEach(productDTO -> {
            Product product = Product.builder()
                                     .position(productDTO.position())
                                     .name(productDTO.name())
                                     .image(productDTO.image())
                                     .price(productDTO.price())
                                     .link(productDTO.link())
                                     .category(category)
                                     .snapshot(snapshot)
                                     .build();
            productRepository.save(product);
        });
    }

    private SnapshotDTO buildSnapshotDTO(Snapshot snapshot) {
        List<Product> products = productRepository.findBySnapshot(snapshot);

        return new SnapshotDTO(
                snapshot.getId(),
                snapshot.getSnapshotDate(),
                products.stream()
                        .collect(Collectors.groupingBy(Product::getCategory))
                        .entrySet()
                        .stream()
                        .map(entry -> new CategoryDTO(
                                entry.getKey().getName(),
                                entry.getKey().getSlug(),
                                entry.getValue().stream()
                                     .map(p -> new ProductDTO(
                                             p.getPosition(),
                                             p.getName(),
                                             p.getImage(),
                                             p.getPrice(),
                                             p.getLink()))
                                     .toList()
                        ))
                        .toList()
        );
    }

}
