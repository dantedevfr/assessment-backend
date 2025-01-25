package com.dantedev.assessment_backend.repositories;

import com.dantedev.assessment_backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // No custom queries needed for generic filtering (handled by GenericRepository)
}