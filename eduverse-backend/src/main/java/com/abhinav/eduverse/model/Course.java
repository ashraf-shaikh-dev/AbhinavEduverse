package com.abhinav.eduverse.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String description;
	private String thumbnailUrl;
	
	//Relation of single teacher is having multiple course
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private User teacher;
	
	//Relation of single Course is having multiple modules
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Module> modules = new ArrayList<>();
	
	//Relation of single Course is having multiple enrollments
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Enrollment> enrollments = new ArrayList<>();
	
	//default constructor
	
	public Course() {
		
	}

	//Constructor with arguments
	
	public Course(long id, String title, String description, String thumbnailUrl, User teacher) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.thumbnailUrl = thumbnailUrl;
		this.teacher = teacher;
	}
	

	//Getters and Setters
	

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
	
	
	
	
	
	
}
