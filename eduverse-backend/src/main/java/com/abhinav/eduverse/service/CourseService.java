package com.abhinav.eduverse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	// To retrieve the courses only created by that specific teacher
	
	public List<Course> getCourseByTeacher(Long teacherId){
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


}
