package com.dantedev.assessment_backend.controllers;

import com.dantedev.assessment_backend.payload.request.LevelRequest;
import com.dantedev.assessment_backend.payload.response.ApiResponse;
import com.dantedev.assessment_backend.payload.response.LevelResponse;
import com.dantedev.assessment_backend.services.LevelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LevelResponse>> createLevel(@Valid @RequestBody LevelRequest levelRequest) {
        LevelResponse levelResponse = levelService.createLevel(levelRequest);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "Level created successfully", levelResponse, null, 201, "/api/levels"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LevelResponse>> getLevelById(@PathVariable Long id) {
        LevelResponse levelResponse = levelService.getLevelById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Level retrieved successfully", levelResponse, null, 200, "/api/levels/" + id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LevelResponse>> updateLevel(
            @PathVariable Long id, @Valid @RequestBody LevelRequest updatedLevelRequest) {
        LevelResponse levelResponse = levelService.updateLevel(id, updatedLevelRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Level updated successfully", levelResponse, null, 200, "/api/levels/" + id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLevel(@PathVariable Long id) {
        levelService.deleteLevel(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Level with ID " + id + " was deleted successfully", null, null, 200, "/api/levels/" + id)
        );
    }

    /**
     * Get all Levels with optional filters, pagination, and sorting.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<LevelResponse>>> getAllLevelsWithFilters(
            @RequestParam Map<String, String> params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        Page<LevelResponse> levels = levelService.getAllLevelsWithFilters(params, pageable);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Levels retrieved successfully", levels, null, 200, "/api/levels")
        );
    }

    /**
     * Helper method to parse sorting parameters.
     */
    private Sort.Order parseSort(String[] sort) {
        String field = sort[0];
        Sort.Direction direction = (sort.length > 1 && sort[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        return new Sort.Order(direction, field);
    }

}