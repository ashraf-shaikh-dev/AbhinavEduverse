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
	
	
	public Enrollment enroll(EnrollmentDTO enrollmentDTO) {
		
		// Checks for the student availability
		User student = userRepository.findById(enrollmentDTO.getStudentId())
				.orElseThrow(() -> new RuntimeException("Student Not Found"));
		
		// Checks for the course availability
		Course course = courseRepository.findById(enrollmentDTO.getCourseId())
				.orElseThrow(() -> new RuntimeException("Course Not Found"));
		
		// Checks if student already enrolled
		if(enrollmentRepository.existsByStudentAndCourse(student, course)) {
			throw new RuntimeException("Student already enrolled in this course");
		}
		
		// Create a new enrollment
		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);
		enrollment.setEnrolledAt(LocalDateTime.now());
		
		// Saves the enrollment
		return enrollmentRepository.save(enrollment);
	}


	public List<User> getStudentsByCourseId(Long courseId) {
	    return enrollmentRepository.findUsersByCourseId(courseId);
	}

	 public List<Course> getCoursesByStudentId(Long studentId) {
	        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
	        return enrollments.stream()
	                          .map(Enrollment::getCourse)
	                          .collect(Collectors.toList());
	    }
	 
	 public List<EnrolledCourseDTO> getEnrolledCoursesWithProgress(Long studentId) {
			List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
			List<EnrolledCourseDTO> dtoList = new ArrayList<>();

			for (Enrollment enrollment : enrollments) {
			    Course course = enrollment.getCourse();

			    List<Module> modules = moduleRepository.findByCourseIdOrderByModuleOrderAsc(course.getId());
			    List<Long> moduleIds = modules.stream().map(Module::getId).toList();

			    int totalModules = modules.size();
			    int completedModules = moduleProgressRepository.countByStudentIdAndModuleIdInAndCompletedTrue(studentId, moduleIds);

			    
			    double progress = (totalModules == 0) ? 0.0 : (completedModules * 100.0) / totalModules;

			    
			    enrollment.setProgress(progress);
			    enrollmentRepository.save(enrollment); 

			    
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
