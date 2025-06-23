import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import '../styles/StudentDashboard.css';

export default function StudentDashboard() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    if (!user || user.role !== 'STUDENT') {
      navigate('/login');
      return;
    }

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
  }, [user, navigate]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/');
  };

  const handleContinueCourse = (courseId) => {
    alert(`Continue course with id: ${courseId}`);
  };

  return (
    <div className="student-dashboard">
      <div className="dashboard-header">
        <motion.h1
          className="dashboard-title"
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          Welcome, {user?.firstName || 'Student'} 👋
        </motion.h1>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>

      <p className="dashboard-subtitle">Here are your enrolled courses and progress:</p>

      {loading && <p className="loading-text">Loading courses...</p>}
      {error && <p className="error-text">{error}</p>}

      {!loading && !error && courses.length === 0 && (
        <p className="no-courses-text">You are not enrolled in any courses yet.</p>
      )}

      <div className="courses-grid">
        {courses.map(course => (
          <motion.div
            className="course-card"
            key={course.id}
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.4 }}
          >
            <img
              src={course.thumbnailUrl || 'https://via.placeholder.com/300x180'}
              alt={course.title}
              className="course-thumbnail"
            />
            <div className="course-info">
              <h3 className="course-title">{course.title}</h3>
              <p className="course-description">{course.description.slice(0, 80)}...</p>
              <p className="course-progress">
                Progress: {course.completedModules ?? 0} / {course.totalModules ?? 0}
              </p>
              <button
                className="continue-btn"
                onClick={() => handleContinueCourse(course.id)}
              >
                Continue Course
              </button>
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
}
