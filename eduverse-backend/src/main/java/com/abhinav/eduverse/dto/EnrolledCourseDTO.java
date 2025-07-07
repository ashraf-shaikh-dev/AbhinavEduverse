package com.abhinav.eduverse.dto;

// DTO to represent a course that a student is enrolled in,
// along with progress details to show how much the student has completed.
public class EnrolledCourseDTO {

    private Long courseId;          // ID of the course
    private String title;           // Title of the course
    private String description;     // Description of the course
    private String thumbnailUrl;    // URL of the course thumbnail image

    private int totalModules;       // Total number of modules in the course
    private int completedModules;   // Number of modules the student has completed

    private double progress;        // Progress percentage (e.g., 75.0 for 75%)

    // Default no-args constructor (needed by frameworks)
    public EnrolledCourseDTO() {}

    // Constructor to quickly create an instance with all data
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
    
    // Getters and setters for all fields - allow reading and modifying values

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
