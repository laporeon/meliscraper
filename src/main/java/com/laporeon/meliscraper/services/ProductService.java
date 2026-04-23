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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String NOT_FOUND_MESSAGE = "Category '%s' not found.";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public PageResponseDTO<ProductDTO> getProductsByCategorySlug(String slug, Pageable pageable) {
        if (!categoryRepository.existsBySlug(slug)) {
            throw new ResourceNotFoundException(NOT_FOUND_MESSAGE.formatted(slug));
        }

        Page<ProductDTO> page = productRepository.findByCategorySlug(slug, pageable)
                                                 .map(productMapper::toSummaryDTO);

        return productMapper.toPageResponseDTO(page);
    }

    @Transactional
    public List<Product> saveProductsFromCategories(Snapshot snapshot, List<CategoryDTO> categories) {
        List<Product> productsToSave = new ArrayList<>();

        categories.forEach(categoryDTO -> {
            Category category = categoryRepository.findBySlug(categoryDTO.slug())
                                                  .orElseGet(() -> categoryRepository.save(categoryMapper.toEntity(categoryDTO)));

            categoryDTO.products().forEach(productDTO -> {
                Product product = productMapper.toEntity(productDTO, category, snapshot);
                productsToSave.add(product);
            });
        });

        return productRepository.saveAll(productsToSave);
    }

    public List<Product> findProductsBySnapshot(Snapshot snapshot) {
        return productRepository.findBySnapshot(snapshot);
    }

}
