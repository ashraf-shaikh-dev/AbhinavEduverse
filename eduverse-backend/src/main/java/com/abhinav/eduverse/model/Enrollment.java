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

@Entity

// Name of the table is enrollments
// student_id and course_id are unique constraints


@Table(name = "enrollments", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "course_id"})
})


public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Shows the time stamp when student enrolled to course
	private LocalDateTime enrolledAt;
	
	// Shows current progress of course
	private double progress = 0.0;
	
	// Relation of having multiple enrollments to one student
	@ManyToOne
	@JoinColumn(name = "student_id")
	private User student;
	
	// Relation of having multiple enrollments to multiple student
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	// Default constructor
	public Enrollment() {}

	// Constructor with arguments
	public Enrollment(LocalDateTime enrolledAt, double progress, User student, Course course) {
		super();
		this.enrolledAt = enrolledAt;
		this.progress = progress;
		this.student = student;
		this.course = course;
	}

	// Getters and setters
	
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
