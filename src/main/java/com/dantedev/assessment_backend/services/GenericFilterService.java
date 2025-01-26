package com.dantedev.assessment_backend.services;

import com.dantedev.assessment_backend.repositories.GenericRepository;
import com.dantedev.assessment_backend.utils.FilterHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenericFilterService<T> {

    private final GenericRepository<T> genericRepository;

    public GenericFilterService(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    /**
     * Retrieve a paginated, filtered, and sorted list of entities.
     *
     * @param entityClass The class of the entity.
     * @param filters     The filters to apply.
     * @param pageable    Pagination and sorting information.
     * @param validFields The valid fields for filtering.
     * @return A paginated list of entities.
     */
    public Page<T> getAllWithFilters(Class<T> entityClass, Map<String, String> filters, Pageable pageable, Map<String, Class<?>> validFields) {
        Map<String, Object> validFilters = FilterHelper.validateAndParseFilters(filters, validFields);
        return genericRepository.findWithFilters(entityClass, validFilters, pageable);
    }

    /**
     * Parse sorting parameters from a string array.
     *
     * @param sort The sort parameters, e.g., ["id", "asc"].
     * @return A Sort.Order object.
     */
    public Sort.Order parseSort(String[] sort) {
        String field = sort[0];
        Sort.Direction direction = (sort.length > 1 && sort[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        return new Sort.Order(direction, field);
    }
}