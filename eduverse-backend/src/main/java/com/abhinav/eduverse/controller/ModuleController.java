package com.abhinav.eduverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.eduverse.service.ModuleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abhinav.eduverse.dto.ModuleDTO;
import com.abhinav.eduverse.model.Module;


@RestController
@RequestMapping("api/modules")
@CrossOrigin(origins = "*")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	
	@PostMapping("/add")
	public Module postMethodName(@RequestBody ModuleDTO moduleDTO) {
		return moduleService.addModule(moduleDTO);
	}
	
}
