package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.ProductDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import com.laporeon.meliscraper.exceptions.SnapshotNotFoundException;
import com.laporeon.meliscraper.helpers.Scraper;
import com.laporeon.meliscraper.repositories.CategoryRepository;
import com.laporeon.meliscraper.repositories.ProductRepository;
import com.laporeon.meliscraper.repositories.SnapshotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public SnapshotDTO getSnapshot() {
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

    public SnapshotDTO findByDate(LocalDate date) {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(date)
                                 .orElseThrow(() -> new SnapshotNotFoundException(date));

        return buildSnapshotDTO(snapshot);
    }
}
