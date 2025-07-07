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

    // Register a new user using UserDTO
    public User registerUser(UserDTO userDTO) {
        // Check if a user with the same email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Create a new User object and set its fields
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        // Save the user in the database
        return userRepository.save(user);
    }

    // Login a user using email and password
    public User loginUser(String email, String password) {
        Optional<User> useropt = userRepository.findByEmail(email);

        // If user exists and password matches, return the user
        if (useropt.isPresent() && useropt.get().getPassword().equals(password)) {
            return useropt.get();
        }

        // Throw error if credentials are invalid
        throw new RuntimeException("Invalid email or password");
    }
}
