package com.dantedev.assessment_backend.controllers;

import com.dantedev.assessment_backend.models.User;
import com.dantedev.assessment_backend.payload.request.CourseRequest;
import com.dantedev.assessment_backend.payload.response.ApiResponse;
import com.dantedev.assessment_backend.payload.response.CourseResponse;
import com.dantedev.assessment_backend.services.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.Map;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Valid @RequestBody CourseRequest courseRequest,
            Principal principal) {

        // Obtener el usuario autenticado desde el contexto
        String username = principal.getName();

        // Pasar el usuario autenticado al servicio
        CourseResponse courseResponse = courseService.createCourse(courseRequest, username);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "Course created successfully", courseResponse, null, 201, "/api/courses"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {
        CourseResponse courseResponse = courseService.getCourseById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course retrieved successfully", courseResponse, null, 200, "/api/courses/" + id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest updatedCourseRequest,
            Principal principal) {
        // Obtener información del usuario autenticado
        String username = principal.getName();
        CourseResponse courseResponse = courseService.updateCourse(id, updatedCourseRequest, username);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course updated successfully", courseResponse, null, 200, "/api/courses/" + id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Course with ID " + id + " was deleted successfully", null, null, 200, "/api/courses/" + id)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CourseResponse>>> getAllCoursesWithFilters(
            @RequestParam Map<String, String> params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        Page<CourseResponse> courses = courseService.getAllCoursesWithFilters(params, pageable);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Courses retrieved successfully", courses, null, 200, "/api/courses")
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