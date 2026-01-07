package com.laporeon.meliscraper.controllers;

import com.laporeon.meliscraper.dtos.*;
import com.laporeon.meliscraper.helpers.SwaggerExamples;
import com.laporeon.meliscraper.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Get list of categories",
            description = "Retrieves a paginated list of categories (name and slug only). " +
                    "Results are always ordered by name in ascending order.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categories list successfully retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.CATEGORY_SUMMARY_PAGE_RESPONSE))),
                    @ApiResponse(responseCode = "429", description = "Too Many Requests",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.ERROR_TOO_MANY_REQUESTS))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.ERROR_INTERNAL_SERVER)))
            }
    )
    @GetMapping
    public ResponseEntity<PageResponseDTO<CategoryDTO>> getCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        PageResponseDTO<CategoryDTO> categories = categoryService.getCategories(pageable);
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
                                    examples = @ExampleObject(value = SwaggerExamples.CATEGORY_PRODUCTS_PAGE_RESPONSE))),
                    @ApiResponse(responseCode = "404", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.CATEGORY_NOT_FOUND_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "429", description = "Too Many Requests",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.ERROR_TOO_MANY_REQUESTS))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.ERROR_INTERNAL_SERVER)))
            }
    )
    @GetMapping("/{slug}/products")
    public ResponseEntity<PageResponseDTO<ProductDTO>> getCategoryProducts(@PathVariable("slug") String slug,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Entity field used for sorting",
                    schema = @Schema(allowableValues = {"name", "price", "link"}),
                    example = "name")
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @Parameter(description = "Sort direction",
                    schema = @Schema(allowableValues = {"ASC", "DESC"}),
                    example = "ASC")
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size,
                                           Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy));
        PageResponseDTO<ProductDTO> products = categoryService.getCategoryProducts(slug, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
