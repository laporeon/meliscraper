package com.laporeon.meliscraper.mappers;

import com.laporeon.meliscraper.dtos.PageResponseDTO;
import com.laporeon.meliscraper.dtos.ProductDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO dto, Category category, Snapshot snapshot) {
        return Product.builder()
               .position(dto.position())
               .name(dto.name())
               .image(dto.image())
               .price(dto.price())
               .link(dto.link())
               .category(category)
               .snapshot(snapshot)
               .build();
    }

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getPosition(),
                product.getName(),
                product.getImage(),
                product.getPrice(),
                product.getLink(),
                null
        );
    }

    public ProductDTO toSummaryDTO(Product product) {
        return new ProductDTO(
                null,
                product.getName(),
                null,
                product.getPrice(),
                product.getLink(),
                product.getSnapshot().getSnapshotDate()
        );
    }

    public PageResponseDTO<ProductDTO> toPageResponseDTO(Page<ProductDTO> page) {
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
