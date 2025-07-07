package com.abhinav.eduverse.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Maps this class to the "users" table in the database
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates unique IDs for users
	private long id;
	
	private String firstName;
	private String lastName;
	
	// Email must be unique, so no two users can register with the same email
	@Column(unique = true)
	private String email;
	
	private String password;
	
	// Role defines if user is STUDENT, TEACHER or ADMIN (stored as string in DB)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	// One user can have many enrollments (a list of courses the user is enrolled in)
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Enrollment> enrollments = new ArrayList<>();
	
	// Getters and setters for all fields below

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	// Constructor with all fields - useful to create user objects easily
	public User(long id, String firstName, String lastName, String email, String password, Role role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	// Default constructor needed by JPA and for creating empty User objects
	public User() {
		super();
	}
}
