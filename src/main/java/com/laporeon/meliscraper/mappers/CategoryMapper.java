package com.laporeon.meliscraper.mappers;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.PageResponseDTO;
import com.laporeon.meliscraper.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ProductMapper productMapper;

    public Category toEntity(CategoryDTO dto) {
        return Category.builder()
                       .name(dto.name())
                       .slug(dto.slug())
                       .build();
    }

    public CategoryDTO toSummaryDTO(Category category) {
        return new CategoryDTO(
                category.getName(),
                category.getSlug(),
                null
        );
    }

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getName(),
                category.getSlug(),
                category.getProducts().stream().map(productMapper::toDTO).toList()
        );
    }


    public PageResponseDTO<CategoryDTO> toPageResponseDTO(Page<CategoryDTO> page) {
        return new PageResponseDTO<>(
                page.getContent(),
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
