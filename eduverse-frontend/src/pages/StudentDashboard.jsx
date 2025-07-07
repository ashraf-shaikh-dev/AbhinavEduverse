import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/StudentDashboard.css';

export default function StudentDashboard() {
  // State to store list of enrolled courses
  const [courses, setCourses] = useState([]);
  // Loading and error states
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();
  // Get logged-in user info from localStorage
  const user = JSON.parse(localStorage.getItem('user'));

  // Run this when component loads
  useEffect(() => {
    // If user is not logged in or not a student, redirect to login
    if (!user || user.role !== 'STUDENT') {
      navigate('/login');
      return;
    }

    // Function to fetch courses the student is enrolled in
    const fetchEnrolledCourses = async () => {
      try {
        // Call backend API to get enrolled courses for this student
        const res = await axios.get(`http://localhost:8080/api/enrollments/student/${user.id}/courses`);
        setCourses(res.data); // Save courses in state
      } catch (err) {
        console.error('Error fetching enrolled courses:', err);
        setError('Failed to load enrolled courses.');
      } finally {
        setLoading(false); // Done loading, even if error
      }
    };

    fetchEnrolledCourses();
  }, [user, navigate]);

  // When user clicks a course card, go to course detail page
  const handleContinueCourse = (courseId) => {
    navigate(`/courses/${courseId}`);
  };

  return (
    <div className="courses-page">
      <div className="courses-header">
        {/* Greet user by first name or fallback */}
        <h1>Welcome, {user?.firstName || 'Student'} 👋</h1>
      </div>

      <p className="meta">Your enrolled courses:</p>

      {/* Show loading or error messages */}
      {loading && <p className="meta">Loading...</p>}
      {error && <p className="meta" style={{ color: 'red' }}>{error}</p>}

      {/* Show message if no courses */}
      {!loading && !error && courses.length === 0 && (
        <p className="meta">You are not enrolled in any courses yet.</p>
      )}

      {/* List of enrolled courses */}
      <div className="courses-grid">
        {courses.map(course => (
          <div
            className="course-card"
            key={course.courseId}
            onClick={() => handleContinueCourse(course.courseId)} // Clicking card goes to course
          >
            <img
              src={course.thumbnailUrl || 'https://via.placeholder.com/300x180'}
              alt={course.title}
              className="course-thumb"
            />
            <div className="course-info">
              <h3>{course.title}</h3>
              <p className="meta">
                {/* Show a short preview of course description */}
                {course.description?.slice(0, 80) || 'No description'}...
              </p>
              <p className="meta">
                {/* Show modules completed out of total */}
                Progress: {course.completedModules} / {course.totalModules} modules
              </p>
              <p className="meta">
                {/* Show percent completion, rounded to 1 decimal */}
                Completion: {course.progress?.toFixed(1) || 0}% complete
              </p>

              {/* Button to continue learning */}
              <button
                className="enroll-btn"
                onClick={(e) => {
                  e.stopPropagation(); // Prevent card click handler
                  handleContinueCourse(course.courseId);
                }}
              >
                Continue Learning
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
