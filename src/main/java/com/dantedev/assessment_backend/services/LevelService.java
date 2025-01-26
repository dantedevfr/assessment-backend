package com.dantedev.assessment_backend.services;

import com.dantedev.assessment_backend.exceptions.BadRequestException;
import com.dantedev.assessment_backend.exceptions.ErrorCodes;
import com.dantedev.assessment_backend.exceptions.ResourceNotFoundException;
import com.dantedev.assessment_backend.models.Level;
import com.dantedev.assessment_backend.payload.request.LevelRequest;
import com.dantedev.assessment_backend.payload.response.LevelResponse;
import com.dantedev.assessment_backend.repositories.GenericRepository;
import com.dantedev.assessment_backend.repositories.LevelRepository;
import com.dantedev.assessment_backend.utils.ErrorCodeGenerator;
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

    public LevelResponse createLevel(LevelRequest levelRequest) {
        if (levelRepository.existsByName(levelRequest.getName())) {
            throw new BadRequestException(
                    "A level with the name '" + levelRequest.getName() + "' already exists.",
                    ErrorCodes.ERROR_INVALID_REQUEST_LEVEL_DUPLICATE_NAME
            );
        }

        Level level = new Level();
        level.setName(levelRequest.getName());
        level.setDescription(levelRequest.getDescription());
        return new LevelResponse(levelRepository.save(level));
    }


    public LevelResponse getLevelById(Long id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Level with ID " + id + " not found",
                        ErrorCodeGenerator.generate("RESOURCE_NOT_FOUND", "LEVEL", null)
                ));
        return new LevelResponse(level);
    }


    public LevelResponse updateLevel(Long id, LevelRequest updatedLevelRequest) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Level with ID " + id + " not found",
                        ErrorCodeGenerator.generate("RESOURCE_NOT_FOUND", "LEVEL", null)
                ));
        level.setName(updatedLevelRequest.getName());
        level.setDescription(updatedLevelRequest.getDescription());
        return new LevelResponse(levelRepository.save(level));
    }

    public void deleteLevel(Long id) {
        if (!levelRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Level with ID " + id + " not found",
                    ErrorCodeGenerator.generate("RESOURCE_NOT_FOUND", "LEVEL", null)
            );
        }
        levelRepository.deleteById(id);
    }

    public Page<LevelResponse> getAllLevelsWithFilters(Map<String, String> filters, Pageable pageable) {
        Map<String, Class<?>> validFields = Map.of(
                "name", String.class,
                "createdAt", LocalDateTime.class,
                "id", Long.class
        );

        Map<String, Object> validFilters = FilterHelper.validateAndParseFilters(filters, validFields);

        return genericRepository.findWithFilters(Level.class, validFilters, pageable)
                .map(LevelResponse::new);
    }
}
