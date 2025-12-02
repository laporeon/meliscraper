package com.laporeon.meliscraper.dtos;

import java.util.List;

public record CategoryDTO(String name,
                          String slug,
                          List<ProductDTO> products) {
}
