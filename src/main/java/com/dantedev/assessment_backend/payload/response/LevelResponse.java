package com.dantedev.assessment_backend.payload.response;

import com.dantedev.assessment_backend.models.Level;

import java.time.LocalDateTime;

public class LevelResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor desde la entidad Level
    public LevelResponse(Level level) {
        this.id = level.getId();
        this.name = level.getName();
        this.description = level.getDescription();
        this.createdAt = level.getCreatedAt();
        this.updatedAt = level.getUpdatedAt();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}