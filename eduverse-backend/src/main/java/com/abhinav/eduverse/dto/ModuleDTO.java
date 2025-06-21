package com.abhinav.eduverse.dto;

public class ModuleDTO {
	
	private String title;
    private String content;
    private String videoUrl;
    private Integer moduleOrder;
    private Long courseId;
    
    // Default Constructor
    public ModuleDTO() {}

    
    // Constructor with arguments
	public ModuleDTO(String title, String content, String videoUrl, Integer moduleOrder, Long courseId) {
		super();
		this.title = title;
		this.content = content;
		this.videoUrl = videoUrl;
		this.moduleOrder = moduleOrder;
		this.courseId = courseId;
	}

	
	// Getters and Setters

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
