package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategorySummaryDTO;
import com.laporeon.meliscraper.dtos.ProductSummaryDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.exceptions.ResourceNotFoundException;
import com.laporeon.meliscraper.repositories.CategoryRepository;
import com.laporeon.meliscraper.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final String NOT_FOUND_MESSAGE = "Category '%s' not found. Check for available categories on: /categories";

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Map<String, List<CategorySummaryDTO>> getCategories() {
        List<CategorySummaryDTO> categories = categoryRepository.findAll()
                                                                    .stream()
                                                                    .map(cat -> new CategorySummaryDTO(
                                                                            cat.getName(),
                                                                            cat.getSlug()))
                                                                    .toList();

        return Map.of("categories", categories);
    }

    public Map<String, List<ProductSummaryDTO>> getCategoryProducts(String slug) {
        Optional<Category> category = categoryRepository.findBySlug(slug);

        if(category.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_MESSAGE.formatted(slug));
        }

        List<ProductSummaryDTO> products = productRepository.findByCategory(category.get())
                                                            .stream()
                                                            .map(p -> new ProductSummaryDTO(
                                                                    p.getName(),
                                                                    p.getPrice(),
                                                                    p.getLink(),
                                                                    p.getSnapshot().getSnapshotDate()))
                                                            .toList();

        return Map.of("products", products);
    }
}
