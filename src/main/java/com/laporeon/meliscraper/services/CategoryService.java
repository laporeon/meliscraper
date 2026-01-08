package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.PageResponseDTO;
import com.laporeon.meliscraper.dtos.ProductDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.entities.Product;
import com.laporeon.meliscraper.entities.Snapshot;
import com.laporeon.meliscraper.exceptions.ResourceNotFoundException;
import com.laporeon.meliscraper.mappers.CategoryMapper;
import com.laporeon.meliscraper.mappers.ProductMapper;
import com.laporeon.meliscraper.repositories.CategoryRepository;
import com.laporeon.meliscraper.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final String NOT_FOUND_MESSAGE = "Category '%s' not found. Check for available categories on: /categories";

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public PageResponseDTO<CategoryDTO> getCategories(Pageable pageable) {
        Page<CategoryDTO> page = categoryRepository.findAll(pageable)
                                                   .map(categoryMapper::toSummaryDTO);

        return categoryMapper.toPageResponseDTO(page);
    }

    public PageResponseDTO<ProductDTO> getCategoryProducts(String slug, Pageable pageable) {
        Category category = categoryRepository.findBySlug(slug)
                                              .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE.formatted(slug)));

        Page<ProductDTO> page = productRepository.findByCategorySlug(category.getSlug(), pageable)
                                                        .map(productMapper::toSummaryDTO);

        System.out.println("Page " + page);

        return productMapper.toPageResponseDTO(page);
    }

    @Transactional
    public void saveProductsFromCategory(Snapshot snapshot, CategoryDTO categoryDTO) {
        Category category = findOrCreateCategory(categoryDTO);

        categoryDTO.products().forEach(productDTO -> {
            Product product = productMapper.toEntity(productDTO, category, snapshot);
            productRepository.save(product);
        });
    }

    private Category findOrCreateCategory(CategoryDTO dto) {
        return categoryRepository.findBySlug(dto.slug())
                                 .orElseGet(() -> categoryRepository.save(categoryMapper.toEntity(dto)));
    }
}
