package com.dantedev.assessment_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GenericRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<T> findWithFilters(Class<T> clazz, Map<String, Object> filters, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> root = query.from(clazz);

        List<Predicate> predicates = new ArrayList<>();

        // Construir predicados dinámicos basados en el tipo de datos
        filters.forEach((key, value) -> {
            if (value != null) {
                if (value instanceof String) {
                    predicates.add(cb.like(cb.lower(root.get(key).as(String.class)), "%" + value.toString().toLowerCase() + "%"));
                } else if (value instanceof Number) {
                    predicates.add(cb.equal(root.get(key), value));
                } else if (value instanceof Boolean) {
                    predicates.add(cb.equal(root.get(key), value));
                } else if (value instanceof java.time.LocalDateTime) {
                    predicates.add(cb.equal(root.get(key), value));
                }
            }
        });

        query.where(predicates.toArray(new Predicate[0]));

        // Aplicar ordenamiento
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));

        // Ejecutar consulta
        var typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(typedQuery.getResultList(), pageable, getTotalCount(clazz, filters));
    }


    private long getTotalCount(Class<T> clazz, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> root = countQuery.from(clazz);

        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((key, value) -> {
            if (value != null) {
                if (value instanceof String) {
                    predicates.add(cb.like(cb.lower(root.get(key).as(String.class)), "%" + value.toString().toLowerCase() + "%"));
                } else if (value instanceof Number) {
                    predicates.add(cb.equal(root.get(key), value));
                } else if (value instanceof java.time.LocalDateTime) {
                    predicates.add(cb.equal(root.get(key), value));
                }
            }
        });

        countQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}