package com.abhinav.eduverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.dto.UserDTO;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allows frontend access
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// API for user sign up
	@PostMapping("/signup")
	public User register(@RequestBody UserDTO userDTO) {
		return userService.registerUser(userDTO);
	}
	
	// API for user login
	@PostMapping("/login")
	public User login(@RequestBody UserDTO userDTO) {
		return userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
	}
	
	

}
