package com.abhinav.eduverse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abhinav.eduverse.dto.CourseDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.ModuleRepository;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    // Create a new course and assign it to a teacher
    public Course createCourse(CourseDTO courseDTO) {
        // Fetch teacher details using teacherId from DTO
        User teacher = userRepository.findById(courseDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Create a new Course object and set its fields
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        course.setDescription(courseDTO.getDescription());
        course.setTeacher(teacher); // Assign the teacher to the course

        // Save the course to the database
        return courseRepository.save(course);
    }

    // Fetch all available courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Fetch all courses created by a specific teacher using teacherId
    public List<Course> getCourseByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    // Fetch a single course using its courseId
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    // Update an existing course's info (title, description, thumbnail)
    public Course updateCourse(Long courseId, CourseDTO courseDTO) {
        // Check if course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Update course details
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setThumbnailUrl(courseDTO.getThumbnailUrl());

        return courseRepository.save(course);
    }

    // Get course details along with how many modules it has
    public CourseDTO getCourseDetailsWithModuleCount(Long courseId) {
        // Fetch the course using courseId
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        // Get list of modules belonging to the course
        List<Module> modules = moduleRepository.findByCourseIdOrderByModuleOrderAsc(course.getId());
        int totalModules = modules.size(); // Count how many modules

        // Create and return CourseDTO with extra info
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setThumbnailUrl(course.getThumbnailUrl());
        dto.setTotalModules(totalModules); // Set module count

        return dto;
    }

    // Get all courses with their module counts included
    public List<CourseDTO> getAllCoursesWithModuleCount() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courses) {
            // For each course, count the number of modules
            int moduleCount = moduleRepository.findByCourseIdOrderByModuleOrderAsc(course.getId()).size();

            // Convert Course to CourseDTO and add extra info
            CourseDTO dto = new CourseDTO();
            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            dto.setDescription(course.getDescription());
            dto.setThumbnailUrl(course.getThumbnailUrl());
            dto.setTeacherId(course.getTeacher() != null ? course.getTeacher().getId() : null);
            dto.setTotalModules(moduleCount);

            courseDTOs.add(dto);
        }

        return courseDTOs;
    }
}
