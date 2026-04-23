package com.laporeon.meliscraper.services;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.PageResponseDTO;
import com.laporeon.meliscraper.entities.Category;
import com.laporeon.meliscraper.mappers.CategoryMapper;
import com.laporeon.meliscraper.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public PageResponseDTO<CategoryDTO> getCategories(Pageable pageable) {
        Page<Category> page = categoryRepository.findAll(pageable);
        return categoryMapper.toPageResponseDTO(page);
    }

}
