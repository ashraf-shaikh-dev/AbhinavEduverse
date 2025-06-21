package com.abhinav.eduverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.ModuleDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.ModuleRepository;
import com.abhinav.eduverse.model.Module;

@Service
public class ModuleService {
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	
	public Module addModule(ModuleDTO moduleDTO) {
		
		// Finding the course by their id
		Course course = courseRepository.findById(moduleDTO.getCourseId())
				.orElseThrow(() -> new RuntimeException("Course not found"));
		
		// Creating a new module using constructor
		
		Module module = new Module(
				moduleDTO.getTitle(),
				moduleDTO.getTitle(),
				moduleDTO.getVideoUrl(),
				moduleDTO.getModuleOrder(),
				course);
		
		return moduleRepository.save(module);
	}

}
