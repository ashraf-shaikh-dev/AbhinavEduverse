package com.abhinav.eduverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.eduverse.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	// To find the user using email
	Optional<User> findByEmail(String email);
	
	// returns true if email already exists
	boolean existsByEmail(String email);

}
