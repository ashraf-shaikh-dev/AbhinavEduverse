package com.abhinav.eduverse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.CourseDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Course createCourse(CourseDTO courseDTO) {
		
		// Search for the teacher
		User teacher = userRepository.findById(courseDTO.getTeacherId())
				.orElseThrow(() -> new RuntimeException("Teacher not found"));
		
		//Create a new course
		Course course = new Course();
		course.setTitle(courseDTO.getTitle());
		course.setThumbnailUrl(courseDTO.getThumbnailUrl());
		course.setDescription(courseDTO.getDescription());
		course.setTeacher(teacher);
		
		//Saving the new course
		return courseRepository.save(course);
	}
	
	// Retrieving a list of all courses
	
	public List<Course> getAllCourses(){
		return courseRepository.findAll();
	}
}
