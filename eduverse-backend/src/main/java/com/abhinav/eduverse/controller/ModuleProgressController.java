package com.abhinav.eduverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	

}
