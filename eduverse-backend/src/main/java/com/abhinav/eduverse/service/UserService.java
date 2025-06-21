package com.abhinav.eduverse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.UserDTO;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User registerUser(UserDTO userDTO) {
		//Checks if the user exists
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new RuntimeException("Email already registered!");
		}
		
		// Creating new user and setting values to the attributes
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());
		
		// Saving the user
		return userRepository.save(user);
	}
	
	// Login using email and password
	public User loginUser(String email, String password) {
		Optional<User> useropt = userRepository.findByEmail(email);
		
		//If user is present and password is correct then login
		if(useropt.isPresent() && useropt.get().getPassword().equals(password)) {
			return useropt.get();
		}
		
		// if credentials are incorrect then throws exception
		throw new RuntimeException("Invalid email or password");
	}
	
}
