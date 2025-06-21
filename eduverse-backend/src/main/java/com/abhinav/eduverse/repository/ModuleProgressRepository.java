package com.abhinav.eduverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.ModuleProgress;
import com.abhinav.eduverse.model.User;

public interface  ModuleProgressRepository extends JpaRepository<ModuleProgress, Long>{
	
	// To find the progress with the help of student and module
	Optional<ModuleProgress> findByStudentAndModule(User student, Module module);
	
	// To find the progress with the help of student
	List<ModuleProgress> findByStudent(User student);
	
	// To find the progress with the help of module
	List<ModuleProgress> findByModule(Module module);

}
