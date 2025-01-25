package com.dantedev.assessment_backend.repositories;

import com.dantedev.assessment_backend.models.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    // Custom queries for Level can be added here if needed
}