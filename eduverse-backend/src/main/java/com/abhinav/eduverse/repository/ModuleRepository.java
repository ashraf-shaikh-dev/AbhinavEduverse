package com.abhinav.eduverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhinav.eduverse.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    // Get all modules for a specific course
    List<Module> findByCourseId(Long courseId);

    // Get all modules for a course sorted by module order (ascending)
    List<Module> findByCourseIdOrderByModuleOrderAsc(Long courseId);
}
