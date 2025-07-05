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

    public Course createCourse(CourseDTO courseDTO) {
        User teacher = userRepository.findById(courseDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        course.setDescription(courseDTO.getDescription());
        course.setTeacher(teacher);

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    public Course updateCourse(Long courseId, CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setThumbnailUrl(courseDTO.getThumbnailUrl());

        return courseRepository.save(course);
    }

    
    public CourseDTO getCourseDetailsWithModuleCount(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        List<Module> modules = moduleRepository.findByCourseIdOrderByModuleOrderAsc(course.getId());
        int totalModules = modules.size();

        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setThumbnailUrl(course.getThumbnailUrl());
        dto.setTotalModules(totalModules);

        return dto;
    }
    public List<CourseDTO> getAllCoursesWithModuleCount() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courses) {
            int moduleCount = moduleRepository.findByCourseIdOrderByModuleOrderAsc(course.getId()).size();

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
