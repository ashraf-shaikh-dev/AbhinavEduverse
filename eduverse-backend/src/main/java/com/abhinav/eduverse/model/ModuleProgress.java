package com.abhinav.eduverse.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "module_progress", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "module_id"})
})
public class ModuleProgress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Shows module completed or not
	private boolean completed;
	
	// Shows timestamp when the module is completed
	private LocalDateTime completedAt;
	
	
	// One student can have multiple completed modules
	@ManyToOne
	@JoinColumn(name = "student_id")
	private User student;
	
	// One course can contain multiple modules
	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;
	
	
	// Default constructor
	public ModuleProgress() {}

	
	// Constructor with arguments
	public ModuleProgress(Long id, boolean completed, LocalDateTime completedAt, User student, Module module) {
		super();
		this.id = id;
		this.completed = completed;
		this.completedAt = completedAt;
		this.student = student;
		this.module = module;
	}

	
	// Getters and setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	
	
}
