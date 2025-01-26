package com.dantedev.assessment_backend.services;

import com.dantedev.assessment_backend.exceptions.BadRequestException;
import com.dantedev.assessment_backend.exceptions.ErrorCodes;
import com.dantedev.assessment_backend.exceptions.ResourceNotFoundException;
import com.dantedev.assessment_backend.models.Course;
import com.dantedev.assessment_backend.models.Level;
import com.dantedev.assessment_backend.models.User;
import com.dantedev.assessment_backend.payload.request.CourseRequest;
import com.dantedev.assessment_backend.payload.response.CourseResponse;
import com.dantedev.assessment_backend.repositories.CourseRepository;
import com.dantedev.assessment_backend.repositories.LevelRepository;
import com.dantedev.assessment_backend.repositories.UserRepository;
import com.dantedev.assessment_backend.utils.ErrorCodeGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LevelRepository levelRepository;
    private final GenericFilterService<Course> genericFilterService;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository,
                         LevelRepository levelRepository,
                         GenericFilterService<Course> genericFilterService,
                         UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.levelRepository = levelRepository;
        this.genericFilterService = genericFilterService;
        this.userRepository = userRepository;
    }

    public CourseResponse createCourse(CourseRequest courseRequest, String username) {
        // Obtener el usuario autenticado desde la base de datos
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with username " + username + " not found",
                        "ERROR_RESOURCE_NOT_FOUND_USER"
                ));

        // Crear la entidad Course
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setContent(courseRequest.getContent());
        course.setLevel(levelRepository.findById(courseRequest.getLevelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Level with ID " + courseRequest.getLevelId() + " not found",
                        "ERROR_RESOURCE_NOT_FOUND_LEVEL"
                )));
        course.setCreatedBy(creator); // Asocia el usuario autenticado como creador
        course.setIsPublic(courseRequest.getIsPublic());
        course.setPrice(courseRequest.getPrice());

        return new CourseResponse(courseRepository.save(course));
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with ID " + id + " not found.",
                        ErrorCodeGenerator.generate("ERROR", "RESOURCE_NOT_FOUND", "COURSE", null)
                ));

        return new CourseResponse(course);
    }

    public CourseResponse updateCourse(Long id, CourseRequest updatedRequest,  String username) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with ID " + id + " not found.",
                        ErrorCodeGenerator.generate("ERROR", "RESOURCE_NOT_FOUND", "COURSE", null)
                ));

        // Validar si se intenta actualizar el nivel
        if (updatedRequest.getLevelId() != null) {
            Level level = levelRepository.findById(updatedRequest.getLevelId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Level with ID " + updatedRequest.getLevelId() + " not found.",
                            ErrorCodeGenerator.generate("ERROR", "RESOURCE_NOT_FOUND", "LEVEL", null)
                    ));
            course.setLevel(level);
        }

        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with username " + username + " not found",
                        "ERROR_RESOURCE_NOT_FOUND_USER"
                ));

        course.setTitle(updatedRequest.getTitle());
        course.setDescription(updatedRequest.getDescription());
        course.setContent(updatedRequest.getContent());
        course.setIsPublic(updatedRequest.getIsPublic());
        course.setPrice(updatedRequest.getPrice());
        course.setCreatedBy(creator);

        return new CourseResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Course with ID " + id + " not found.",
                    ErrorCodeGenerator.generate("ERROR", "RESOURCE_NOT_FOUND", "COURSE", null)
            );
        }

        courseRepository.deleteById(id);
    }

    public Page<CourseResponse> getAllCoursesWithFilters(Map<String, String> filters, Pageable pageable) {
        Map<String, Class<?>> validFields = Map.of(
                "title", String.class,
                "createdAt", LocalDateTime.class,
                "id", Long.class,
                "isPublic", Boolean.class,
                "price", Double.class
        );

        return genericFilterService
                .getAllWithFilters(Course.class, filters, pageable, validFields)
                .map(CourseResponse::new);
    }
}