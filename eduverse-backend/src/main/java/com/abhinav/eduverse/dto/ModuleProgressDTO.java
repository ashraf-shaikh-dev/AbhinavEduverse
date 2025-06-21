package com.abhinav.eduverse.dto;

public class ModuleProgressDTO {
	private Long studentId;
	private Long moduleId;
	private boolean completed;
	
	//Default Constructor
	public ModuleProgressDTO() {}

	
	//Constructor with arguments
	public ModuleProgressDTO(Long studentId, Long moduleId, boolean completed) {
		super();
		this.studentId = studentId;
		this.moduleId = moduleId;
		this.completed = completed;
	}

	// Getters and setters
	
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

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
}
