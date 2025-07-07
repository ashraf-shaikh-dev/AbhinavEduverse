package com.abhinav.eduverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.User;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Check if student is already enrolled in a course
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);

    // Returns true if student is already enrolled
    boolean existsByStudentAndCourse(User student, Course course);

    // Get list of users enrolled in a course using courseId
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findUsersByCourseId(@Param("courseId") Long courseId);

    // Get all enrollments of a specific student
    List<Enrollment> findByStudentId(Long studentId);
}
