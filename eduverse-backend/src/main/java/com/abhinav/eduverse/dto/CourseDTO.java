package com.abhinav.eduverse.dto;

public class CourseDTO {
	private String title;
	private String description;
	private String thumbnailUrl;
	private Long teacherId;
	
	// Default Constructor
	
	public CourseDTO() {}
	
	//Constructor with arguments

	public CourseDTO(String title, String description, String thumbnailUrl, Long teacherId) {
		super();
		this.title = title;
		this.description = description;
		this.thumbnailUrl = thumbnailUrl;
		this.teacherId = teacherId;
	}
	
	//Getters and Setters

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
	
	

}
