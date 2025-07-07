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
// Table "module_progress" with a unique constraint on (student_id, module_id) to prevent duplicate progress records
@Table(name = "module_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "module_id"})
})
public class ModuleProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
    private Long id;
    
    // Whether the student has completed this module or not
    private boolean completed;
    
    // Timestamp when the module was marked completed
    private LocalDateTime completedAt;
    
    // Many progress records can belong to one student
    @ManyToOne
    @JoinColumn(name = "student_id") // Foreign key column in this table for student
    private User student;
    
    // Many progress records can belong to one module
    @ManyToOne
    @JoinColumn(name = "module_id")  // Foreign key column in this table for module
    private Module module;
    
    // Default no-argument constructor needed by JPA
    public ModuleProgress() {}

    // Constructor with all fields
    public ModuleProgress(Long id, boolean completed, LocalDateTime completedAt, User student, Module module) {
        super();
        this.id = id;
        this.completed = completed;
        this.completedAt = completedAt;
        this.student = student;
        this.module = module;
    }

    // Getters and setters for all fields

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
