package com.laporeon.meliscraper.controllers;

import com.laporeon.meliscraper.dtos.SnapshotDTO;
import com.laporeon.meliscraper.services.SnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/snapshots")
@RequiredArgsConstructor
public class SnapshotController {

    private final SnapshotService snapshotService;

    @GetMapping
    public ResponseEntity<SnapshotDTO> getSnapshot() {
        SnapshotDTO snapshotDTO = snapshotService.getSnapshot();
        return ResponseEntity.status(HttpStatus.OK).body(snapshotDTO);
    }
}
