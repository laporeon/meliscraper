package com.laporeon.meliscraper.controllers;

import com.laporeon.meliscraper.dtos.CategorySummaryDTO;
import com.laporeon.meliscraper.dtos.ErrorResponseDTO;
import com.laporeon.meliscraper.dtos.ProductDTO;
import com.laporeon.meliscraper.dtos.ProductSummaryDTO;
import com.laporeon.meliscraper.helpers.SwaggerConstants;
import com.laporeon.meliscraper.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Get list of categories",
            description = "Retrieves a list of existing categories (name and slug only).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategorySummaryDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.CATEGORY_SUMMARY_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @GetMapping
    public ResponseEntity<List<CategorySummaryDTO>> getCategories() {
        List<CategorySummaryDTO> categories = categoryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @Operation(
            summary = "List category products",
            description = "Retrieves a list of products associated to the specified category slug.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.PRODUCTS_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "404", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.CATEGORY_NOT_FOUND_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @GetMapping("/{slug}/products")
    public ResponseEntity<List<ProductSummaryDTO>> getCategoryProducts(@PathVariable("slug") String slug) {
        List<ProductSummaryDTO> products = categoryService.getCategoryProducts(slug);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
