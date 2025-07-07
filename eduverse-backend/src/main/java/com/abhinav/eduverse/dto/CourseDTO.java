package com.abhinav.eduverse.dto;

// Data Transfer Object for Course
// Used to transfer course data between backend and frontend without exposing entity details
public class CourseDTO {
	
	private Long id;               // Unique identifier of the course
	private String title;          // Title of the course
	private String description;    // Description of the course
	private String thumbnailUrl;   // URL for the course thumbnail image
	private Long teacherId;        // ID of the teacher who created the course
	private int totalModules;      // Total number of modules in the course
	private int enrolledStudents;  // Number of students enrolled in the course

	// Default constructor - required for frameworks and serialization
	public CourseDTO() {
	}

	// Constructor with parameters for quick creation of CourseDTO object
	public CourseDTO(String title, String description, String thumbnailUrl, Long teacherId, int totalModules) {
		super();
		this.title = title;
		this.description = description;
		this.thumbnailUrl = thumbnailUrl;
		this.teacherId = teacherId;
		this.totalModules = totalModules;
	}

	// Getters and Setters for all fields to access and modify private variables
	
	public int getTotalModules() {
		return totalModules;
	}

	public void setTotalModules(int totalModules) {
		this.totalModules = totalModules;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getEnrolledStudents() {
		return enrolledStudents;
	}

	public void setEnrolledStudents(int enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}

}
