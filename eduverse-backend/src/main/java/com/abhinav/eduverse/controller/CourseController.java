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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
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
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}
	
	
	
}
