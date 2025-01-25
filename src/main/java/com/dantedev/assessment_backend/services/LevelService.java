package com.dantedev.assessment_backend.services;

import com.dantedev.assessment_backend.models.Level;
import com.dantedev.assessment_backend.repositories.GenericRepository;
import com.dantedev.assessment_backend.repositories.LevelRepository;
import com.dantedev.assessment_backend.utils.FilterHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class LevelService {

    private final LevelRepository levelRepository;
    private final GenericRepository<Level> genericRepository;

    public LevelService(LevelRepository levelRepository, GenericRepository<Level> genericRepository) {
        this.levelRepository = levelRepository;
        this.genericRepository = genericRepository;
    }

    public Level createLevel(Level level) {
        return levelRepository.save(level);
    }

    public Page<Level> getAllLevels(Pageable pageable) {
        return levelRepository.findAll(pageable);
    }

    public Level getLevelById(Long id) {
        return levelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Level not found"));
    }

    public Level updateLevel(Long id, Level updatedLevel) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Level not found"));
        level.setName(updatedLevel.getName());
        level.setDescription(updatedLevel.getDescription());
        return levelRepository.save(level);
    }

    public void deleteLevel(Long id) {
        if (!levelRepository.existsById(id)) {
            throw new RuntimeException("Level not found");
        }
        levelRepository.deleteById(id);
    }

    public Page<Level> getAllLevelsWithFilters(Map<String, String> filters, Pageable pageable) {
        // Define los campos válidos y sus tipos para la entidad Level
        Map<String, Class<?>> validFields = Map.of(
                "name", String.class,
                "createdAt", LocalDateTime.class,
                "id", Long.class
        );

        // Validar y transformar los filtros
        Map<String, Object> validFilters = FilterHelper.validateAndParseFilters(filters, validFields);

        // Pasar los filtros validados al repositorio genérico
        return genericRepository.findWithFilters(Level.class, validFilters, pageable);
    }
}
