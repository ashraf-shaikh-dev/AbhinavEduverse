package com.abhinav.eduverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.dto.CourseDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.service.CourseService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController  // Marks this class as a REST controller, so Spring can handle HTTP requests here
@RequestMapping("/api/courses")  // Base URL path for all APIs in this controller
@CrossOrigin(origins = "*")  // Allow cross-origin requests from any origin (for frontend-backend communication)
public class CourseController {

    // Inject CourseService so we can use its business logic methods here
    @Autowired
    private CourseService courseService;

    // API endpoint for creating a new course
    // Accepts CourseDTO JSON in request body and returns created Course object
    @PostMapping("/create")
    public Course create(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    // API endpoint to get all courses with count of their modules
    // Returns a list of CourseDTOs that contain course info + module count
    @GetMapping("/all")
    public List<CourseDTO> getAllCoursesWithModuleCount() {
        return courseService.getAllCoursesWithModuleCount();
    }

    // API to get all courses created by a specific teacher using teacher's ID from URL path
    @GetMapping("/teacher/{teacherId}")
    public List<Course> getCourseByTeacher(@PathVariable Long teacherId) {
        return courseService.getCourseByTeacher(teacherId);
    }

    // API to get detailed course info with module count by course ID from URL path
    @GetMapping("/{courseId}")
    public CourseDTO getCourseDetailsWithModuleCount(@PathVariable Long courseId) {
        return courseService.getCourseDetailsWithModuleCount(courseId);
    }

    // API to update an existing course's details by course ID from URL path
    // Accepts updated CourseDTO data in request body and returns updated Course object
    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        return courseService.updateCourse(courseId, courseDTO);
    }
}
