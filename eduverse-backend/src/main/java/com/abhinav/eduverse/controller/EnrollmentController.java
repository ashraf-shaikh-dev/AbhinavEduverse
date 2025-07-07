package com.abhinav.eduverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.dto.EnrolledCourseDTO;
import com.abhinav.eduverse.dto.EnrollmentDTO;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.service.EnrollmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController  // This tells Spring this class handles REST API calls
@RequestMapping("/api/enrollments")  // Base path for all enrollment-related APIs
@CrossOrigin(origins = "*")  // Allow all origins for CORS, useful for frontend-backend interaction
public class EnrollmentController {
	
	// Inject EnrollmentService to use business logic methods
	@Autowired
	private EnrollmentService enrollmentService;
	
	// API endpoint to enroll a student in a course
	// Receives enrollment data (studentId, courseId) in the request body as JSON
	// Returns the created Enrollment object after successful enrollment
	@PostMapping("/enroll")
	public Enrollment enrollStudent(@RequestBody EnrollmentDTO enrollmentDTO) {
		return enrollmentService.enroll(enrollmentDTO);
	}
	
	// API to get list of all students enrolled in a specific course by course ID
	@GetMapping("/course/{courseId}")
	public List<User> getEnrolledStudents(@PathVariable Long courseId) {
		return enrollmentService.getStudentsByCourseId(courseId);
	}
	
	// API to get all courses a student is enrolled in, along with their progress
	// The student ID is passed as a path variable
	// Returns a list of EnrolledCourseDTO which contains course details + progress info
	@GetMapping("/student/{studentId}/courses")
	public List<EnrolledCourseDTO> getEnrolledCoursesWithProgress(@PathVariable Long studentId) {
		return enrollmentService.getEnrolledCoursesWithProgress(studentId);
	}
}
