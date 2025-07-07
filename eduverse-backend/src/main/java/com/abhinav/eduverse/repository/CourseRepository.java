package com.abhinav.eduverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhinav.eduverse.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Get all courses created by a specific teacher
    List<Course> findByTeacherId(Long teacherId);
}
