package com.abhinav.eduverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.service.ModuleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abhinav.eduverse.dto.ModuleDTO;
import com.abhinav.eduverse.model.Module;
import java.util.List;


@RestController
@RequestMapping("api/modules")
@CrossOrigin(origins = "*")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	
	@PostMapping("/create")
	public Module postMethodName(@RequestBody ModuleDTO moduleDTO) {
		 System.out.println("Received module: " + moduleDTO);
		return moduleService.addModule(moduleDTO);
	}
	@GetMapping("/course/{courseId}")
	public List<Module> getModulesByCourse(@PathVariable Long courseId) {
	    return moduleService.getModulesByCourseId(courseId);
	}
	@PutMapping("/modules/{id}")
	public Module updateModule(@PathVariable Long id, @RequestBody Module updatedModule) {
	    return moduleService.updateModule(id, updatedModule);
	}

}
