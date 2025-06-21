import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/global.css';

export default function StudentDashboard() {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    // Fetch user from localStorage
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || user.role !== 'STUDENT') {
      alert('Unauthorized! Login as student.');
      return;
    }

    // Fetch enrolled courses from backend
    axios.get(`http://localhost:8080/api/students/${user.id}/courses`)
      .then(res => {
        setCourses(res.data);
      })
      .catch(err => {
        console.error('Error fetching courses:', err);
        alert('Failed to load courses');
      });
  }, []);

  return (
    <div className="container">
      <h2>Welcome, Student!</h2>
      <h3>Your Courses</h3>
      {courses.length === 0 ? (
        <p>No courses enrolled yet.</p>
      ) : (
        <ul>
          {courses.map(course => (
            <li key={course.courseId}>
              <strong>{course.courseName}</strong><br />
              Progress: {course.completedModules}/{course.totalModules} modules
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
