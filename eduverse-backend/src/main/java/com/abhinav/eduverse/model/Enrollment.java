package com.abhinav.eduverse.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity  // Marks this class as a JPA entity
@Table(
    name = "enrollments", // Maps to "enrollments" table in DB
    uniqueConstraints = {
        // Ensures combination of student_id and course_id is unique
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
    }
)
public class Enrollment {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
    private Long id;

    // Timestamp when the student enrolled in the course
    private LocalDateTime enrolledAt;

    // Progress of the student in the course (0.0 to 100.0)
    private double progress = 0.0;

    // Many enrollments can belong to one student
    @ManyToOne
    @JoinColumn(name = "student_id") // Foreign key column for student
    private User student;

    // Many enrollments can belong to one course
    @ManyToOne
    @JoinColumn(name = "course_id") // Foreign key column for course
    private Course course;

    // Default no-argument constructor (required by JPA)
    public Enrollment() {}

    // Constructor to create an Enrollment with values
    public Enrollment(LocalDateTime enrolledAt, double progress, User student, Course course) {
        super();
        this.enrolledAt = enrolledAt;
        this.progress = progress;
        this.student = student;
        this.course = course;
    }

    // Getters and setters for all fields
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
