package com.abhinav.eduverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.dto.UserDTO;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController  // This marks the class as a REST controller to handle HTTP requests
@RequestMapping("/api/users")  // Base URL for user-related APIs
@CrossOrigin(origins = "*")  // Allow frontend apps from any origin to call these APIs
public class UserController {
	
	@Autowired  // Inject UserService to access user-related business logic
	private UserService userService;
	
	// POST API to register a new user (sign up)
	@PostMapping("/signup")
	public User register(@RequestBody UserDTO userDTO) {
		// Call service method to create user using data from request body (UserDTO)
		return userService.registerUser(userDTO);
	}
	
	// POST API for user login (authentication)
	@PostMapping("/login")
	public User login(@RequestBody UserDTO userDTO) {
		// Pass email and password to service for validating and returning user info
		return userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
	}
}
