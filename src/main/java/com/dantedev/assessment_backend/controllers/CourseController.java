package com.dantedev.assessment_backend.controllers;

import com.dantedev.assessment_backend.models.Course;
import com.dantedev.assessment_backend.payload.request.CourseRequest;
import com.dantedev.assessment_backend.payload.response.CourseResponse;
import com.dantedev.assessment_backend.services.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Create a new course.
     */
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse course = courseService.createCourse(request);
        return ResponseEntity.status(201).body(course);
    }


    @GetMapping
    public Page<Course> getCoursesWithFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long levelId,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));

        Map<String, Object> filters = new HashMap<>();
        filters.put("title", title);
        filters.put("levelId", levelId);
        filters.put("content", content);

        return courseService.getCoursesWithFilters(filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        CourseResponse course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        CourseResponse updatedCourse = courseService.updateCourse(id, request);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course with ID " + id + " was deleted successfully.");
    }

    private Sort.Order parseSort(String[] sort) {
        return new Sort.Order(
                (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) ? Sort.Direction.DESC : Sort.Direction.ASC,
                sort[0]
        );
    }
}