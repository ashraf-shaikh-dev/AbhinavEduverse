package com.abhinav.eduverse.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.eduverse.dto.EnrolledCourseDTO;
import com.abhinav.eduverse.dto.EnrollmentDTO;
import com.abhinav.eduverse.model.Course;
import com.abhinav.eduverse.model.Enrollment;
import com.abhinav.eduverse.model.Module;
import com.abhinav.eduverse.model.User;
import com.abhinav.eduverse.repository.CourseRepository;
import com.abhinav.eduverse.repository.EnrollmentRepository;
import com.abhinav.eduverse.repository.ModuleProgressRepository;
import com.abhinav.eduverse.repository.ModuleRepository;
import com.abhinav.eduverse.repository.UserRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleProgressRepository moduleProgressRepository;

    // Enroll a student in a course
    public Enrollment enroll(EnrollmentDTO enrollmentDTO) {

        // Check if student exists
        User student = userRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        // Check if course exists
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course Not Found"));

        // Prevent duplicate enrollment
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        // Create a new enrollment object
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        // Save to database
        return enrollmentRepository.save(enrollment);
    }

    // Get all students enrolled in a course
    public List<User> getStudentsByCourseId(Long courseId) {
        return enrollmentRepository.findUsersByCourseId(courseId);
    }

    // Get all courses a student is enrolled in
    public List<Course> getCoursesByStudentId(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        // Extract the course from each enrollment
        return enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
    }

    // Get enrolled courses for a student with progress percentage
    public List<EnrolledCourseDTO> getEnrolledCoursesWithProgress(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<EnrolledCourseDTO> dtoList = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();

            // Get all modules of the course
            List<Module> modules = moduleRepository.findByCourseIdOrderByModuleOrderAsc(course.getId());

            // Extract module IDs
            List<Long> moduleIds = modules.stream().map(Module::getId).toList();

            int totalModules = modules.size();

            // Count how many modules the student has completed
            int completedModules = moduleProgressRepository.countByStudentIdAndModuleIdInAndCompletedTrue(studentId, moduleIds);

            // Calculate progress in percentage
            double progress = (totalModules == 0) ? 0.0 : (completedModules * 100.0) / totalModules;

            // Update progress in enrollment table
            enrollment.setProgress(progress);
            enrollmentRepository.save(enrollment);

            // Prepare DTO to return
            EnrolledCourseDTO dto = new EnrolledCourseDTO(
                    course.getId(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getThumbnailUrl(),
                    totalModules,
                    completedModules,
                    progress
            );

            dtoList.add(dto);
        }

        return dtoList;
    }
}
