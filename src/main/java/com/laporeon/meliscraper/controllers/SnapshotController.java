package com.laporeon.meliscraper.controllers;

import com.laporeon.meliscraper.dtos.ErrorResponseDTO;
import com.laporeon.meliscraper.dtos.SnapshotSummaryDTO;
import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.helpers.SwaggerConstants;
import com.laporeon.meliscraper.services.SnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/snapshots")
@RequiredArgsConstructor
@Tag(name = "Snapshot")
public class SnapshotController {

    private final SnapshotService snapshotService;

    @Operation(
            summary = "Get all snapshots or daily snapshot",
            description = "Retrieves snapshots based on query parameter. If 'all=true', returns a list of all available " +
                    "snapshots (id and date only). If 'all=false' or empty, returns the daily snapshot with full details " +
                    "including categories and products.",
            parameters = {
                    @Parameter(
                            name = "all",
                            description = "If true, returns all snapshots summary. If false, returns today's full snapshot"
                    )
            },
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
    public ResponseEntity<?> getSnapshots(@RequestParam(defaultValue = "false") boolean all) {

        if (all) {
            Map<String, List<SnapshotSummaryDTO>> snapshots = snapshotService.getSnapshots();
            return ResponseEntity.status(HttpStatus.OK).body(snapshots);
        }

        SnapshotDTO snapshotDTO = snapshotService.getDailySnapshot();
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
                                    examples = @ExampleObject(value = SwaggerConstants.SNAPSHOT_NOT_FOUND_ERROR_MESSAGE))),
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
