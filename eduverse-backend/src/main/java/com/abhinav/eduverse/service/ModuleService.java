package com.abhinav.eduverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.abhinav.eduverse.dto.ModuleDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.ModuleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Add a new module to a specific course
    public Module addModule(ModuleDTO moduleDTO) {
        // Find course using courseId from DTO
        Course course = courseRepository.findById(moduleDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + moduleDTO.getCourseId()));

        // Create a new module and link it with the course
        Module module = new Module(
                moduleDTO.getTitle(),
                moduleDTO.getContent(),
                moduleDTO.getVideoUrl(),
                moduleDTO.getModuleOrder(),
                course);

        // Save the module to the database
        return moduleRepository.save(module);
    }

    // Get all modules of a course, sorted by their order
    public List<Module> getModulesByCourseId(Long courseId) {
        return moduleRepository.findByCourseIdOrderByModuleOrderAsc(courseId);
    }

    // Update an existing module's details
    public Module updateModule(Long id, Module updatedModule) {
        // Fetch the module from DB
        Module existingModule = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));

        // Update its fields
        existingModule.setTitle(updatedModule.getTitle());
        existingModule.setContent(updatedModule.getContent());
        existingModule.setVideoUrl(updatedModule.getVideoUrl());

        return moduleRepository.save(existingModule);
    }

    // Delete a module by its ID
    public void deleteModule(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new RuntimeException("Module not found with id " + moduleId);
        }

        moduleRepository.deleteById(moduleId);
    }
}
