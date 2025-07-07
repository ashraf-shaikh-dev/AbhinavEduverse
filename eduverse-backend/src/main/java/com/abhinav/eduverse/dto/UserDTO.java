package com.abhinav.eduverse.dto;

import com.abhinav.eduverse.model.Role;

// Data Transfer Object for User data exchange between frontend and backend
public class UserDTO {
	
	// User's first name
	private String firstName;
	
	// User's last name
	private String lastName;
	
	// User's email, used for login and identification
	private String email;
	
	// User's password (should be stored securely in real apps)
	private String password;
	
	// User role: can be USER, TEACHER, or ADMIN (defines permissions)
	private Role role;
	
	// Constructor to create a UserDTO with all fields
	public UserDTO(String firstName, String lastName, String email, String password, Role role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	// Default no-args constructor (required for serialization/deserialization)
	public UserDTO() {
	}

	// Getter and setter methods for each property
	
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
}
