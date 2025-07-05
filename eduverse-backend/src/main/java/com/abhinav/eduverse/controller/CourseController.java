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
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	// API for creating new course
	
	@PostMapping("/create")
	public Course create(@RequestBody CourseDTO courseDTO) {
		return courseService.createCourse(courseDTO); 
	}
	
	// API for retrieving all courses
	 @GetMapping("/all")
	    public List<CourseDTO> getAllCoursesWithModuleCount() {
	        return courseService.getAllCoursesWithModuleCount();
	    }
	
	// API for retrieve the courses only created by that specific teacher
	
	@GetMapping("/teacher/{teacherId}")
	public List<Course> getCourseByTeacher(@PathVariable Long teacherId) {
		return courseService.getCourseByTeacher(teacherId);
	}
	
	@GetMapping("/{courseId}")
	public CourseDTO getCourseDetailsWithModuleCount(@PathVariable Long courseId) {
	    return courseService.getCourseDetailsWithModuleCount(courseId);
	}

	
	@PutMapping("/{courseId}")
	public Course updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
	    return courseService.updateCourse(courseId, courseDTO);
	}

	
	
	
	
	
}
