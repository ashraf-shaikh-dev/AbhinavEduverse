package com.abhinav.eduverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.abhinav.eduverse.dto.EnrolledCourseDTO;
import com.abhinav.eduverse.dto.ModuleDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.ModuleProgress;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.EnrollmentRepository;
import com.abhinav.eduverse.repository.ModuleProgressRepository;
import com.abhinav.eduverse.repository.ModuleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private CourseRepository courseRepository;

	

	public Module addModule(ModuleDTO moduleDTO) {
	    Course course = courseRepository.findById(moduleDTO.getCourseId())
	            .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + moduleDTO.getCourseId()));

	    Module module = new Module(moduleDTO.getTitle(), moduleDTO.getContent(), moduleDTO.getVideoUrl(),
	            moduleDTO.getModuleOrder(), course);

	    return moduleRepository.save(module);
	}

	public List<Module> getModulesByCourseId(Long courseId) {
		return moduleRepository.findByCourseIdOrderByModuleOrderAsc(courseId);
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
