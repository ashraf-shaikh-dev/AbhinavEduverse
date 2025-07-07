package com.abhinav.eduverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.dto.ModuleProgressDTO;
import com.abhinav.eduverse.model.ModuleProgress;
import com.abhinav.eduverse.service.ModuleProgressService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController  // Marks this as a REST controller for handling HTTP requests
@RequestMapping("/api/progress/")  // Base URL path for all module progress-related APIs
@CrossOrigin(origins = "*")  // Allow requests from any origin (frontend communication)
public class ModuleProgressController {
	
	// Injecting ModuleProgressService to handle business logic
	@Autowired
	private ModuleProgressService moduleProgressService;
	
	// POST API to update module progress (e.g. saving current progress of a student in a module)
	@PostMapping("/update")
	public ModuleProgress update(@RequestBody ModuleProgressDTO moduleProgressDTO) {
		return moduleProgressService.updateProgress(moduleProgressDTO);  // Delegate update to service
	}
	
	// POST API to mark a module as complete for a student
	@PostMapping("/complete")
    public ResponseEntity<?> markModuleComplete(@RequestBody ModuleProgressDTO dto) {
        try {
            ModuleProgress progress = moduleProgressService.updateProgress(dto); // Update progress to completed
            return ResponseEntity.ok(progress);  // Return success response with updated progress
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Return error message if update fails
        }
    }
	
	// GET API to fetch all module progress for a student in a specific course
	@GetMapping("/student/{studentId}/course/{courseId}")
    public List<ModuleProgressDTO> getModuleProgressForStudentCourse(
            @PathVariable Long studentId,  // Student's ID in path variable
            @PathVariable Long courseId) {  // Course ID in path variable
        return moduleProgressService.getProgressByStudentAndCourse(studentId, courseId);  // Return progress list
    }

}
