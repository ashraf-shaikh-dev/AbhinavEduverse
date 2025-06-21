package com.abhinav.eduverse.dto;

public class EnrollmentDTO {

	private Long studentId;
	private Long courseId;
	
	// Default Constructor
	public EnrollmentDTO() {}

	// Constructor with arguments
	public EnrollmentDTO(Long studentId, Long courseId) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
	}

	// Getters and Setters
	
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
