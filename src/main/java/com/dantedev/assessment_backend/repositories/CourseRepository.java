package com.dantedev.assessment_backend.repositories;

import com.dantedev.assessment_backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Check if a course with the given title exists for the specified level.
     *
     * @param title The title of the course.
     * @param levelId The ID of the level.
     * @return True if a course with the given title and level exists, otherwise false.
     */
    boolean existsByTitleAndLevelId(String title, Long levelId);

    /**
     * Find all public courses (optional: add pagination in the service layer).
     *
     * @return A list of public courses.
     */
    List<Course> findAllByIsPublicTrue();
}