package com.abhinav.eduverse.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.ModuleProgressDTO;
import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.ModuleProgress;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.ModuleProgressRepository;
import com.abhinav.eduverse.repository.ModuleRepository;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class ModuleProgressService {

	@Autowired
	private ModuleProgressRepository moduleProgressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	public ModuleProgress updateProgress(ModuleProgressDTO moduleProgressDTO) {
		
		// Checking if student exists
		User student = userRepository.findById(moduleProgressDTO.getStudentId())
				.orElseThrow(() -> new RuntimeException("Student not found"));
		
		// Checking if module exists
		Module module = moduleRepository.findById(moduleProgressDTO.getModuleId())
				.orElseThrow(() -> new RuntimeException("Module not found"));
		
		// Creating a new module progress report
		ModuleProgress progress = moduleProgressRepository
				.findByStudentAndModule(student, module)
				.orElse(new ModuleProgress());
		
		// Assigning a values to the progress
		progress.setStudent(student);
		progress.setModule(module);
		progress.setCompleted(moduleProgressDTO.isCompleted());
		progress.setCompletedAt(moduleProgressDTO.isCompleted() ? LocalDateTime.now() : null);
		
		// Saving the progress
		return moduleProgressRepository.save(progress);
	}
}
