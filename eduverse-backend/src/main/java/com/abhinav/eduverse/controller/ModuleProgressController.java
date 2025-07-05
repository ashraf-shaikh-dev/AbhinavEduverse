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


@RestController
@RequestMapping("/api/progress/")
@CrossOrigin(origins = "*")
public class ModuleProgressController {
	
	@Autowired
	private ModuleProgressService moduleProgressService;
	
	// API to update module progress
	@PostMapping("/update")
	public ModuleProgress update(@RequestBody ModuleProgressDTO moduleProgressDTO) {
		return moduleProgressService.updateProgress(moduleProgressDTO);
	}
	
	@PostMapping("/complete")
    public ResponseEntity<?> markModuleComplete(@RequestBody ModuleProgressDTO dto) {
        try {
            ModuleProgress progress = moduleProgressService.updateProgress(dto);
            return ResponseEntity.ok(progress);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	@GetMapping("/student/{studentId}/course/{courseId}")
    public List<ModuleProgressDTO> getModuleProgressForStudentCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return moduleProgressService.getProgressByStudentAndCourse(studentId, courseId);
    }

}
