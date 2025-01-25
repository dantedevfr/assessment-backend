package com.dantedev.assessment_backend.controllers;

import com.dantedev.assessment_backend.models.Level;
import com.dantedev.assessment_backend.repositories.LevelRepository;
import com.dantedev.assessment_backend.services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @PostMapping
    public ResponseEntity<Level> createLevel(@Valid @RequestBody Level level) {
        return ResponseEntity.status(201).body(levelService.createLevel(level));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Level>> getAllLevels(Pageable pageable) {
        return ResponseEntity.ok(levelService.getAllLevels(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> getLevelById(@PathVariable Long id) {
        return ResponseEntity.ok(levelService.getLevelById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Level> updateLevel(@PathVariable Long id, @Valid @RequestBody Level updatedLevel) {
        return ResponseEntity.ok(levelService.updateLevel(id, updatedLevel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLevel(@PathVariable Long id) {
        levelService.deleteLevel(id);
        return ResponseEntity.ok("Level with ID " + id + " was deleted successfully.");
    }

    /**
     * Get all Levels with optional filters, pagination, and sorting.
     */
    @GetMapping
    public ResponseEntity<Page<Level>> getAllLevels(
            @RequestParam Map<String, String> params, // Recoge todos los filtros dinámicos
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));

        // Pasa todos los filtros dinámicos al servicio
        Page<Level> levels = levelService.getAllLevelsWithFilters(params, pageable);
        return ResponseEntity.ok(levels);
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