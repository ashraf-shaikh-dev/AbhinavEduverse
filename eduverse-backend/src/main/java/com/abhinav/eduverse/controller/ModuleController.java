package com.abhinav.eduverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController  // Marks this class as a REST controller to handle HTTP requests
@RequestMapping("api/modules")  // Base URL path for all module related endpoints
@CrossOrigin(origins = "*")  // Allows requests from any origin (for frontend-backend communication)
public class ModuleController {

    // Injecting ModuleService to delegate business logic
    @Autowired
    private ModuleService moduleService;

    // POST request to create a new module using data from request body
    @PostMapping("/create")
    public Module postMethodName(@RequestBody ModuleDTO moduleDTO) {
        System.out.println("Received module: " + moduleDTO);  // Logging received module DTO for debugging
        return moduleService.addModule(moduleDTO);  // Delegates module creation to service and returns the created module
    }

    // GET request to get all modules belonging to a specific course
    @GetMapping("/course/{courseId}")
    public List<Module> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.getModulesByCourseId(courseId);  // Calls service to fetch modules by course ID
    }

    // PUT request to update an existing module by ID with data from request body
    @PutMapping("/{id}")
    public Module updateModule(@PathVariable Long id, @RequestBody Module updatedModule) {
        return moduleService.updateModule(id, updatedModule);  // Delegate update operation to service
    }

    // DELETE request to delete a module by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable("id") Long moduleId) {
        moduleService.deleteModule(moduleId);  // Calls service to delete the module
        return ResponseEntity.noContent().build();  // Return 204 No Content response to indicate successful deletion
    }
}
