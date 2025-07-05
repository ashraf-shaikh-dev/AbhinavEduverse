package com.abhinav.eduverse.dto;

public class CourseDTO {
	private Long id;
	private String title;
	private String description;
	private String thumbnailUrl;
	private Long teacherId;
	private int totalModules;
	
	// Default Constructor
	
	public CourseDTO() {}
	
	//Constructor with arguments

	public CourseDTO(String title, String description, String thumbnailUrl, Long teacherId, int totalModules) {
		super();
		this.title = title;
		this.description = description;
		this.thumbnailUrl = thumbnailUrl;
		this.teacherId = teacherId;
		this.totalModules = totalModules;
	}
	
	//Getters and Setters

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
	
	
	

}
