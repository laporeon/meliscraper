package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.PageResponseDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import com.laporeon.meliscraper.exceptions.ResourceNotFoundException;
import com.laporeon.meliscraper.helpers.Scraper;
import com.laporeon.meliscraper.mappers.SnapshotMapper;
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
    private final ProductService productService;

    @Transactional(readOnly = true)
    public PageResponseDTO<SnapshotDTO> getSnapshotsSummary(Pageable pageable) {
        Page<SnapshotDTO> page = snapshotRepository.findAll(pageable)
                                                   .map(snapshotMapper::toSummaryDTO);

        return snapshotMapper.toPageResponseDTO(page);
    }

    @Transactional
    public SnapshotDTO getTodaySnapshot() {
        return snapshotRepository.findBySnapshotDate(LocalDate.now())
                                 .map(snapshot -> {
                                     List<Product> products = productService.findProductsBySnapshot(snapshot);
                                     return snapshotMapper.toDTO(snapshot, products);
                                 })
                                 .orElseGet(this::createNewSnapshot);
    }

    private SnapshotDTO createNewSnapshot() {
        List<CategoryDTO> categoriesDTOList = scraper.scrape();
        Snapshot snapshot = snapshotRepository.save(Snapshot.builder()
                                                            .snapshotDate(LocalDate.now())
                                                            .build());

        List<Product> products = productService.saveProductsFromCategories(snapshot, categoriesDTOList);

        return snapshotMapper.toDTO(snapshot, products);
    }

    public SnapshotDTO findByDate(LocalDate date) {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(date)
                                              .orElseThrow(() -> new ResourceNotFoundException(
                                                      NOT_FOUND_MESSAGE.formatted(date)));

        List<Product> products = productService.findProductsBySnapshot(snapshot);

        return snapshotMapper.toDTO(snapshot, products);
    }

    @Transactional
    public void deleteSnapshotByDate(LocalDate date) {
        Snapshot snapshot = snapshotRepository.findBySnapshotDate(date)
                                              .orElseThrow(() -> new ResourceNotFoundException(
                                                      NOT_FOUND_MESSAGE.formatted(date)));

        snapshotRepository.delete(snapshot);
    }
}
