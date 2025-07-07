package com.abhinav.eduverse.dto;

// DTO class for transferring module progress data
public class ModuleProgressDTO {
	
	// ID of the student who is progressing through the module
	private Long studentId;
	
	// ID of the module being progressed/completed
	private Long moduleId;
	
	// Flag to indicate if the module is completed by the student
	private boolean completed;
	
	// Default no-argument constructor (needed for some frameworks)
	public ModuleProgressDTO() {}

	// Constructor to quickly create an object with all required fields
	public ModuleProgressDTO(Long studentId, Long moduleId, boolean completed) {
		super();
		this.studentId = studentId;
		this.moduleId = moduleId;
		this.completed = completed;
	}

	// Getters and setters for each property
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	// Getter for completion status
	public boolean isCompleted() {
		return completed;
	}

	// Setter for completion status
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
}
