package com.laporeon.meliscraper.mappers;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.PageResponseDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SnapshotMapper {

    private final ProductMapper productMapper;

    public SnapshotDTO toSummaryDTO(Snapshot snapshot) {
        return new SnapshotDTO(
                snapshot.getId(),
                snapshot.getSnapshotDate(),
                null
        );
    }

    public SnapshotDTO toDTO(Snapshot snapshot, List<Product> products) {
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
                                     .map(productMapper::toDTO)
                                     .toList()
                        ))
                        .toList()
        );
    }

    public PageResponseDTO<SnapshotDTO> toPageResponseDTO(Page<SnapshotDTO> page) {
        return new PageResponseDTO<>(
                page.getContent().stream().toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                page.getSort().isSorted(),
                page.getSort().isUnsorted()
        );
    }
}
