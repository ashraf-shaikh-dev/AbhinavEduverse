package com.abhinav.eduverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.dto.EnrollmentDTO;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.service.EnrollmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	// API to enroll student
	@PostMapping("/enroll")
	public Enrollment enrollStudent(@RequestBody EnrollmentDTO enrollmentDTO) {
		return enrollmentService.enroll(enrollmentDTO);
	}
	
	 @GetMapping("/course/{courseId}")
	    public List<User> getEnrolledStudents(@PathVariable Long courseId) {
	        return enrollmentService.getStudentsByCourseId(courseId);
	    }
	
	
}
