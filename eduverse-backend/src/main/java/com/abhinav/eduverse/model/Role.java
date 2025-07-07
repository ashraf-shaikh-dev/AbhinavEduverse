package com.abhinav.eduverse.model;

// Enum to define different user roles in the system
// These roles help control what a user can do or access
public enum Role {
    STUDENT,  // Normal user who can enroll and learn courses
    TEACHER,  // User who can create and manage courses
    ADMIN     // User with full control over the platform (manage users, courses, etc.)
}
