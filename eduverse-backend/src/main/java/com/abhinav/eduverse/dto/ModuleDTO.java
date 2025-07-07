package com.abhinav.eduverse.dto;

// DTO class for Module data transfer between frontend and backend
public class ModuleDTO {
	
	// Title of the module
	private String title;
	
	// Content/description of the module
    private String content;
    
    // URL of the video associated with this module (if any)
    private String videoUrl;
    
    // Order number to define module's sequence in the course
    private Integer moduleOrder;
    
    // ID of the course this module belongs to
    private Long courseId;
    
    // Default constructor (required by some frameworks)
    public ModuleDTO() {}

    // Constructor with all fields for easy object creation
	public ModuleDTO(String title, String content, String videoUrl, Integer moduleOrder, Long courseId) {
		super();
		this.title = title;
		this.content = content;
		this.videoUrl = videoUrl;
		this.moduleOrder = moduleOrder;
		this.courseId = courseId;
	}

	// Getters and setters for all fields below

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getModuleOrder() {
		return moduleOrder;
	}

	public void setModuleOrder(Integer moduleOrder) {
		this.moduleOrder = moduleOrder;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
}
