package com.laporeon.meliscraper.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDTO(
        String name,
        String slug,
        List<ProductDTO> products

) {
}

