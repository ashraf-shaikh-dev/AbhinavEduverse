package com.abhinav.eduverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.ModuleProgress;
import com.abhinav.eduverse.model.User;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Long> {

    // Get progress of a specific student for a specific module
    Optional<ModuleProgress> findByStudentAndModule(User student, Module module);

    // Get all progress records for a student
    List<ModuleProgress> findByStudent(User student);

    // Get all progress records for a module
    List<ModuleProgress> findByModule(Module module);

    // Get completed modules by a student
    List<ModuleProgress> findByStudentIdAndCompletedTrue(Long studentId);

    // Count how many modules from a list are completed by a student
    int countByStudentIdAndModuleIdInAndCompletedTrue(Long studentId, List<Long> moduleIds);

    // Get progress using studentId and moduleId
    Optional<ModuleProgress> findByStudentIdAndModuleId(Long studentId, Long moduleId);

    // Get progress for a student in a specific course (using custom query)
    @Query("SELECT mp FROM ModuleProgress mp WHERE mp.student.id = :studentId AND mp.module.course.id = :courseId")
    List<ModuleProgress> findByStudentIdAndModuleCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
