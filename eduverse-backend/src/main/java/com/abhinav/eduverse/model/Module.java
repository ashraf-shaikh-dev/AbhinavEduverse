package com.abhinav.eduverse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "modules")
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	
	@Column(length = 5000)
	private String content;
	
	private String videoUrl;
	
	// Order of the module
	private Integer moduleOrder;
	
	
	// One course having many modules
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	//Default constructor
	public Module() {}


	//Constructor with arguments
	
	public Module(String title, String content, String videoUrl, Integer moduleOrder, Course course) {
		super();
		this.title = title;
		this.content = content;
		this.videoUrl = videoUrl;
		this.moduleOrder = moduleOrder;
		this.course = course;
	}


	// Getters and Setters
	
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

