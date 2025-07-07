package com.abhinav.eduverse.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.ModuleProgressDTO;
import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.ModuleProgress;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.ModuleProgressRepository;
import com.abhinav.eduverse.repository.ModuleRepository;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class ModuleProgressService {

    @Autowired
    private ModuleProgressRepository moduleProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    // Method to update progress (completed or not) for a module by a student
    public ModuleProgress updateProgress(ModuleProgressDTO moduleProgressDTO) {

        // Check if the student exists
        User student = userRepository.findById(moduleProgressDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if the module exists
        Module module = moduleRepository.findById(moduleProgressDTO.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found"));

        // Find existing progress or create a new one
        ModuleProgress progress = moduleProgressRepository
                .findByStudentAndModule(student, module)
                .orElse(new ModuleProgress());

        // Set values for the progress
        progress.setStudent(student);
        progress.setModule(module);
        progress.setCompleted(moduleProgressDTO.isCompleted());

        // If completed, set completed time, else set it to null
        progress.setCompletedAt(moduleProgressDTO.isCompleted() ? LocalDateTime.now() : null);

        // Save to database
        return moduleProgressRepository.save(progress);
    }

    // Mark a specific module as completed for a student
    public void markModuleCompleted(Long studentId, Long moduleId) {
        Optional<ModuleProgress> existing = moduleProgressRepository.findByStudentIdAndModuleId(studentId, moduleId);

        if (existing.isPresent()) {
            ModuleProgress mp = existing.get();
            if (!mp.isCompleted()) {
                mp.setCompleted(true);
                mp.setCompletedAt(LocalDateTime.now());
                moduleProgressRepository.save(mp);
            }
        } else {
            // If no progress record exists, create a new one
            ModuleProgress mp = new ModuleProgress();
            mp.setStudent(userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("User not found")));
            mp.setModule(moduleRepository.findById(moduleId)
                    .orElseThrow(() -> new RuntimeException("Module not found")));
            mp.setCompleted(true);
            mp.setCompletedAt(LocalDateTime.now());
            moduleProgressRepository.save(mp);
        }
    }

    // Get module progress for a student in a specific course
    public List<ModuleProgressDTO> getProgressByStudentAndCourse(Long studentId, Long courseId) {
        // Fetch progress records from DB
        List<ModuleProgress> progressList = moduleProgressRepository
                .findByStudentIdAndModuleCourseId(studentId, courseId);

        // Convert each record to DTO and return
        return progressList.stream()
                .map(mp -> new ModuleProgressDTO(
                        mp.getStudent().getId(),
                        mp.getModule().getId(),
                        mp.isCompleted()
                ))
                .collect(Collectors.toList());
    }
}
