package com.laporeon.meliscraper.controllers;

import com.laporeon.meliscraper.dtos.*;
import com.laporeon.meliscraper.helpers.SwaggerExamples;
import com.laporeon.meliscraper.services.SnapshotService;
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

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/snapshots")
@RequiredArgsConstructor
@Tag(name = "Snapshot")
public class SnapshotController {

    private final SnapshotService snapshotService;

    @Operation(
            summary = "Get all snapshots summary",
            description = "Returns a paginated and sorted list of snapshots summary (id and snapshotDate only), " +
                    "allowing control over page number and size. Results are always ordered by snapshotDate in ascending order.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Snapshots summary list successfully retrieved",
                            content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SnapshotDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.SNAPSHOT_SUMMARY_PAGE_RESPONSE))),
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
    public ResponseEntity<PageResponseDTO<SnapshotDTO>> getSnapshotsSummary(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("snapshotDate").ascending());

        PageResponseDTO<SnapshotDTO> snapshots = snapshotService.getSnapshotsSummary(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(snapshots);

    }

    @Operation(
            summary = "Get today's snapshot",
            description = "Retrieves today's snapshot with full details including categories and products. If snapshot doesn't exist, creates a new one.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Snapshot successfully retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SnapshotDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SNAPSHOT_DETAIL_RESPONSE))),
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
    @GetMapping("/today")
    public ResponseEntity<SnapshotDTO> getTodaySnapshot() {
        SnapshotDTO snapshot = snapshotService.getTodaySnapshot();
        return ResponseEntity.status(HttpStatus.OK).body(snapshot);

    }

    @Operation(
            summary = "Find snapshot by date",
            description = "Receives a date in yyyy-MM-dd format and returns snapshot if exists",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SnapshotDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SNAPSHOT_DETAIL_RESPONSE))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SNAPSHOT_NOT_FOUND_ERROR_MESSAGE))),
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
    @GetMapping("/{date}")
    public ResponseEntity<SnapshotDTO> findSnapshotByDate(
            @Parameter(description = "Snapshot date in ISO format (yyyy-MM-dd)", required = true)
            @PathVariable("date")LocalDate date) {
        SnapshotDTO snapshotDTO = snapshotService.findByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(snapshotDTO);
    }

    @Operation(
            summary = "Delete snapshot by date",
            description = "Deletes a snapshot and all associated products by date. Categories remain intact.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Snapshot and related products successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SNAPSHOT_NOT_FOUND_ERROR_MESSAGE))),
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
    @DeleteMapping("/{date}")
    public ResponseEntity<SnapshotDTO> deleteSnapshotByDate(
            @Parameter(description = "Snapshot date in ISO format (yyyy-MM-dd)", required = true)
            @PathVariable("date") LocalDate date) {
         snapshotService.deleteSnapshotByDate(date);
        return ResponseEntity.noContent().build();
    }
}
