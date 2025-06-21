package com.abhinav.eduverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.User;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
	
	// Checks id student is already enrolled in course
	Optional<Enrollment> findByStudentAndCourse(User student, Course course);
	
	
	boolean existsByStudentAndCourse(User student, Course course);

}
