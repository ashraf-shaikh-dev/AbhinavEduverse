package com.abhinav.eduverse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity  // Marks this class as a JPA entity mapped to the database table "modules"
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated primary key
    private Long id;

    // Title of the module (e.g., "Introduction to Java")
    private String title;

    // Content of the module, allows large text (up to 5000 characters)
    @Column(length = 5000)
    private String content;

    // URL of the video associated with this module, if any
    private String videoUrl;

    // Defines the order of this module in the course sequence (e.g., 1, 2, 3...)
    private Integer moduleOrder;

    // Many modules belong to one course
    @ManyToOne
    @JoinColumn(name = "course_id")  // Foreign key column for course in "modules" table
    @JsonBackReference  // Prevents infinite recursion during JSON serialization
    private Course course;

    // Default no-argument constructor (required by JPA)
    public Module() {}

    // Constructor with all fields except id (which is auto-generated)
    public Module(String title, String content, String videoUrl, Integer moduleOrder, Course course) {
        super();
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.moduleOrder = moduleOrder;
        this.course = course;
    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
