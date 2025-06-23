package com.abhinav.eduverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhinav.eduverse.model.Course;

@Repository
public interface CourseRepository  extends JpaRepository<Course, Long>{
	
	// To retrieve the courses only created by that specific teacher
	
	List<Course> findByTeacherId(Long teacherId);

}
