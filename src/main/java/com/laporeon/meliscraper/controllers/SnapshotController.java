package com.laporeon.meliscraper.controllers;

import com.laporeon.meliscraper.dtos.ErrorResponseDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.helpers.SwaggerConstants;
import com.laporeon.meliscraper.services.SnapshotService;
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

import java.time.LocalDate;

@RestController
@RequestMapping("/snapshots")
@RequiredArgsConstructor
@Tag(name = "Snapshots")
public class SnapshotController {

    private final SnapshotService snapshotService;

    @Operation(
            summary = "Get daily snapshot",
            description = "Retrieves the daily snapshot if it exists. Otherwise, creates a new snapshot for the current date " +
                    "and returns its data. Ensures a unique snapshot per day to avoid duplicates and provides associated category information.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SnapshotDTO.class),
                            examples = @ExampleObject(value = SwaggerConstants.SNAPSHOT_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @GetMapping
    public ResponseEntity<SnapshotDTO> getSnapshot() {
        SnapshotDTO snapshotDTO = snapshotService.getSnapshot();
        return ResponseEntity.status(HttpStatus.OK).body(snapshotDTO);
    }

    @Operation(
            summary = "Find snapshot by date",
            description = "Receives a date in yyyy-MM-dd format and returns snapshot if exists",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SnapshotDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.SNAPSHOT_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.NOT_FOUND_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerConstants.INTERNAL_ERROR_MESSAGE)))
            }
    )
    @GetMapping("/{date}")
    public ResponseEntity<SnapshotDTO> findSnapshotByDate(@PathVariable("date")LocalDate date) {
        SnapshotDTO snapshotDTO = snapshotService.findByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(snapshotDTO);
    }
}
