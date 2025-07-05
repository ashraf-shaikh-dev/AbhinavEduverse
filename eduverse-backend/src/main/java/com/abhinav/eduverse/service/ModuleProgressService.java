package com.abhinav.eduverse.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	public void markModuleCompleted(Long studentId, Long moduleId) {
	    // Check if already marked complete
	    Optional<ModuleProgress> existing = moduleProgressRepository.findByStudentIdAndModuleId(studentId, moduleId);

	    if (existing.isPresent()) {
	        ModuleProgress mp = existing.get();
	        if (!mp.isCompleted()) {
	            mp.setCompleted(true);
	            mp.setCompletedAt(LocalDateTime.now());
	            moduleProgressRepository.save(mp);
	        }
	    } else {
	        // Create new progress entry
	        ModuleProgress mp = new ModuleProgress();
	        mp.setStudent(userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("User not found")));
	        mp.setModule(moduleRepository.findById(moduleId).orElseThrow(() -> new RuntimeException("Module not found")));
	        mp.setCompleted(true);
	        mp.setCompletedAt(LocalDateTime.now());
	        moduleProgressRepository.save(mp);
	    }
	}

	public List<ModuleProgressDTO> getProgressByStudentAndCourse(Long studentId, Long courseId) {
		List<ModuleProgress> progressList = moduleProgressRepository.findByStudentIdAndModuleCourseId(studentId, courseId);


    // Convert ModuleProgress to DTO as needed
		 return progressList.stream()
		            .map(mp -> new ModuleProgressDTO(
		            		mp.getStudent().getId(),
		                    mp.getModule().getId(),
		                    mp.isCompleted()
		            ))
		            .collect(Collectors.toList());
}

	
}
