package com.laporeon.meliscraper.repositories;

import com.laporeon.meliscraper.entities.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, UUID> {

    Optional<Snapshot> findBySnapshotDate(LocalDate date);

}
