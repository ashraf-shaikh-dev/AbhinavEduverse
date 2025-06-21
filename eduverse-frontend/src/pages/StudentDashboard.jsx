import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';  // For logout redirect
import '../styles/StudentDashboard.css';

/**
 * StudentDashboard component
 * Shows the list of courses a student is enrolled in along with progress info.
 */
export default function StudentDashboard() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  // Fetch courses on mount
  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const res = await axios.get('http://localhost:8080/api/courses/all');
        setCourses(res.data);
      } catch (err) {
        console.error('Error fetching courses:', err);
        setError('Failed to load courses.');
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  // Handle logout: clear session and redirect to login
  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/login');
  };

  // Placeholder for continuing a course
  const handleContinueCourse = (courseId) => {
    // TODO: Navigate to course detail page or player
    alert(`Continue course with id: ${courseId}`);
  };

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1 className="dashboard-title">Student Dashboard</h1>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </header>

      <p className="dashboard-welcome">
        Welcome back! Here are your current courses and progress:
      </p>

      {loading && <p className="loading-text">Loading courses...</p>}

      {error && <p className="error-text">{error}</p>}

      {!loading && !error && courses.length === 0 && (
        <p className="no-courses-text">You are not enrolled in any courses yet. Browse and enroll now!</p>
      )}

      {!loading && !error && courses.length > 0 && (
        <div className="courses-grid">
          {courses.map(course => (
            <div key={course.id} className="course-card">
              <h2 className="course-title">{course.title}</h2>
              <p className="course-description">{course.description}</p>
              <p className="course-progress">
                Progress: {course.completedModules ?? 0} / {course.totalModules ?? 0} modules completed
              </p>
              <button
                className="continue-btn"
                onClick={() => handleContinueCourse(course.id)}
              >
                Continue Course
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
