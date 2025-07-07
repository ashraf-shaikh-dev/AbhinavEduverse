package com.abhinav.eduverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation tells Spring Boot that this is the main application class
@SpringBootApplication
public class EduverseApplication {

    // This is the entry point of the backend application
    public static void main(String[] args) {
        // This line starts the entire Spring Boot application
        SpringApplication.run(EduverseApplication.class, args);
    }

}
