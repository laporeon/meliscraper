package com.laporeon.meliscraper.exceptions;

import java.time.LocalDate;

public class SnapshotNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No snapshot found for date %s";

    public SnapshotNotFoundException(LocalDate snapshotDate) {
        super(DEFAULT_MESSAGE.formatted(snapshotDate));
    }
}
