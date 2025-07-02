package com.abhinav.eduverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.EnrollmentDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.EnrollmentRepository;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class EnrollmentService {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	
	public Enrollment enroll(EnrollmentDTO enrollmentDTO) {
		
		// Checks for the student availability
		User student = userRepository.findById(enrollmentDTO.getStudentId())
				.orElseThrow(() -> new RuntimeException("Student Not Found"));
		
		// Checks for the course availability
		Course course = courseRepository.findById(enrollmentDTO.getCourseId())
				.orElseThrow(() -> new RuntimeException("Course Not Found"));
		
		// Checks if student already enrolled
		if(enrollmentRepository.existsByStudentAndCourse(student, course)) {
			throw new RuntimeException("Student already enrolled in this course");
		}
		
		// Create a new enrollment
		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);
		enrollment.setEnrolledAt(LocalDateTime.now());
		
		// Saves the enrollment
		return enrollmentRepository.save(enrollment);
	}


	public List<User> getStudentsByCourseId(Long courseId) {
	    return enrollmentRepository.findUsersByCourseId(courseId);
	}


}
