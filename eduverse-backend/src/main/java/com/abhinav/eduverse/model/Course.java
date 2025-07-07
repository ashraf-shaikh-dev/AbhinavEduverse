package com.abhinav.eduverse.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity  // Marks this class as a JPA entity mapped to a database table
@Table(name = "courses") // Maps to "courses" table in the database
public class Course {

    @Id  // Primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates unique IDs
    private long id;

    private String title; // Course title

    private String description; // Course description

    private String thumbnailUrl; // URL of course thumbnail image

    // Relation: Many courses can be created by one teacher
    @ManyToOne
    @JoinColumn(name = "teacher_id") // Foreign key column for teacher
    private User teacher;

    // Relation: One course contains multiple modules
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) 
    private List<Module> modules = new ArrayList<>();

    // Relation: One course can have many enrollments (students)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    // Default no-argument constructor (needed by JPA)
    public Course() {
    }

    // Constructor to create a Course with initial data
    public Course(long id, String title, String description, String thumbnailUrl, User teacher) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.teacher = teacher;
    }

    // Getters and setters for all fields
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    // Calculate total number of modules in this course (used in DTOs and responses)
    @JsonProperty("totalModules") 
    public int getTotalModules() {
        return modules != null ? modules.size() : 0;
    }

    // Calculate total number of enrolled students for this course
    @JsonProperty("enrolledStudents")
    public int getEnrolledStudents() {
        return enrollments != null ? enrollments.size() : 0;
    }
}
