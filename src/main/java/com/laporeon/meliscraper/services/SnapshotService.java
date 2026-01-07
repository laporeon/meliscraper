package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.*;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import com.laporeon.meliscraper.exceptions.ResourceNotFoundException;
import com.laporeon.meliscraper.helpers.Scraper;
import com.laporeon.meliscraper.mappers.SnapshotMapper;
import com.laporeon.meliscraper.repositories.ProductRepository;
import com.laporeon.meliscraper.repositories.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private static final String NOT_FOUND_MESSAGE = "No snapshots found for date '%s'";

    private final Scraper scraper;
    private final SnapshotMapper snapshotMapper;
    private final SnapshotRepository snapshotRepository;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageResponseDTO<SnapshotDTO> getSnapshotsSummary(Pageable pageable) {

        Page<SnapshotDTO> page = snapshotRepository.findAll(pageable)
                                                        .map(snapshotMapper::toSummaryDTO);

        return snapshotMapper.toPageResponseDTO(page);
    }

    @Transactional
    public SnapshotDTO getTodaySnapshot() {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(LocalDate.now())
                                              .orElseGet(this::createNewSnapshot);
        List<Product> products = productRepository.findBySnapshot(snapshot);
        return snapshotMapper.toDTO(snapshot, products);
    }

    @Transactional
    private Snapshot createNewSnapshot() {
        List<CategoryDTO> categoriesDTOList = scraper.scrape();
        Snapshot snapshot = snapshotRepository.save(Snapshot.builder().build());
        categoriesDTOList.forEach(cat -> categoryService.saveProductsFromCategory(snapshot, cat));
        return snapshot;
    }

    @Transactional(readOnly = true)
    public SnapshotDTO findByDate(LocalDate date) {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(date)
                                              .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE.formatted(date)));

        List<Product> products = productRepository.findBySnapshot(snapshot);

        return snapshotMapper.toDTO(snapshot, products);
    }

}
