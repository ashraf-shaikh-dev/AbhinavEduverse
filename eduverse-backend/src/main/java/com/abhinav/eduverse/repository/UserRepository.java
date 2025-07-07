package com.abhinav.eduverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.eduverse.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email
    Optional<User> findByEmail(String email);

    // Check if a user with the given email already exists
    boolean existsByEmail(String email);
}
