package com.abhinav.eduverse.dto;

public class EnrolledCourseDTO {

    private Long courseId;
    private String title;
    private String description;
    private String thumbnailUrl;

    private int totalModules;
    private int completedModules;

    private double progress;  

    // Default constructor
    public EnrolledCourseDTO() {}

    // Constructor with all fields
    public EnrolledCourseDTO(Long courseId, String title, String description, String thumbnailUrl,
                             int totalModules, int completedModules, double progress) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.totalModules = totalModules;
        this.completedModules = completedModules;
        this.progress = progress;
    }
    

    // Getters and setters
    

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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

	public int getTotalModules() {
		return totalModules;
	}

	public void setTotalModules(int totalModules) {
		this.totalModules = totalModules;
	}

	public int getCompletedModules() {
		return completedModules;
	}

	public void setCompletedModules(int completedModules) {
		this.completedModules = completedModules;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

}
