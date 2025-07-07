package com.abhinav.eduverse.dto;

// DTO used when a student enrolls in a course.
// It contains the IDs of the student and the course for the enrollment request.
public class EnrollmentDTO {

	private Long studentId;  // ID of the student who wants to enroll
	private Long courseId;   // ID of the course the student wants to enroll in
	
	// Default no-args constructor - needed for some frameworks
	public EnrollmentDTO() {}

	// Constructor to easily create an EnrollmentDTO with studentId and courseId
	public EnrollmentDTO(Long studentId, Long courseId) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
	}

	// Getters and setters to read and update the fields
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
}
