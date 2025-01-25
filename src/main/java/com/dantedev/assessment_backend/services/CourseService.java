package com.dantedev.assessment_backend.services;

import com.dantedev.assessment_backend.models.Course;
import com.dantedev.assessment_backend.models.Level;
import com.dantedev.assessment_backend.models.User;
import com.dantedev.assessment_backend.payload.request.CourseRequest;
import com.dantedev.assessment_backend.payload.response.CourseResponse;
import com.dantedev.assessment_backend.repositories.CourseRepository;
import com.dantedev.assessment_backend.repositories.GenericRepository;
import com.dantedev.assessment_backend.repositories.LevelRepository;
import com.dantedev.assessment_backend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final GenericRepository<Course> genericRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository,
                         LevelRepository levelRepository, GenericRepository<Course> genericRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.levelRepository = levelRepository;
        this.genericRepository = genericRepository;
    }

    public CourseResponse createCourse(CourseRequest request) {
        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Level level = levelRepository.findById(request.getLevelId())
                .orElse(null);

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCreatedBy(creator);
        course.setLevel(level);
        course.setContent(request.getContent());
        course.setIsPublic(request.getIsPublic());
        course.setPrice(request.getPrice());

        return toCourseResponse(courseRepository.save(course));
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return toCourseResponse(course);
    }

    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Level level = levelRepository.findById(request.getLevelId())
                .orElse(null);

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCreatedBy(creator);
        course.setLevel(level);
        course.setContent(request.getContent());
        course.setIsPublic(request.getIsPublic());
        course.setPrice(request.getPrice());

        return toCourseResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    public Page<Course> getCoursesWithFilters(Map<String, Object> filters, Pageable pageable) {
        return genericRepository.findWithFilters(Course.class, filters, pageable);
    }

    private CourseResponse toCourseResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCreatorName(course.getCreatedBy().getUsername());
        response.setLevelName(course.getLevel() != null ? course.getLevel().getName() : null);
        response.setIsPublic(course.getIsPublic());
        response.setPrice(course.getPrice());
        response.setCreatedAt(course.getCreatedAt());
        response.setUpdatedAt(course.getUpdatedAt());
        return response;
    }
}