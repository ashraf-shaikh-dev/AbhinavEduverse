package com.abhinav.eduverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


	public List<Module> getModulesByCourseId(Long courseId) {
	    return moduleRepository.findByCourseIdOrderByModuleOrderAsc(courseId);
	}
	
	public Module addModule(Module module) {
		Long courseId = module.getCourse() != null ? module.getCourse().getId() : null;
		if (courseId == null) {
		    throw new IllegalArgumentException("Course ID must not be null");
		}



	    Course course = courseRepository.findById(module.getCourse().getId())
	        .orElseThrow(() -> new RuntimeException("Course not found"));

	    module.setCourse(course);
	    return moduleRepository.save(module);
	}
	
	public Module updateModule(Long id, Module updatedModule) {
	    Module existingModule = moduleRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));

	    existingModule.setTitle(updatedModule.getTitle());
	    existingModule.setContent(updatedModule.getContent());
	    existingModule.setVideoUrl(updatedModule.getVideoUrl());

	    return moduleRepository.save(existingModule);
	}
	
	  public void deleteModule(Long moduleId) {
	        if (!moduleRepository.existsById(moduleId)) {
	            throw new RuntimeException("Module not found with id " + moduleId);
	        }
	        moduleRepository.deleteById(moduleId);
	    }

	



}
