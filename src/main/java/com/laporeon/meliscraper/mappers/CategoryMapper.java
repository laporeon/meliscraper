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
    
    public PageResponseDTO<CategoryDTO> toPageResponseDTO(Page<Category> page) {
        return new PageResponseDTO<>(
                page.getContent().stream().map(this::toSummaryDTO).toList(),
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
