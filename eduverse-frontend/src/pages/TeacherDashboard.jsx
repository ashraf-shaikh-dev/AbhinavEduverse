import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/TeacherDashboard.css';

export default function TeacherDashboard() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Get current user from localStorage
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    if (!user || user.role !== 'TEACHER') {
      navigate('/login');
      return;
    }

    const fetchCourses = async () => {
      try {
        const res = await axios.get('http://localhost:8080/api/courses/all'); // Replace with teacher-specific API if needed
        setCourses(res.data);
      } catch (err) {
        setError('Failed to load courses');
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, [user, navigate]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/login');
  };

  return (
    <div className="teacher-dashboard">
      <div className="dashboard-header">
        <h1>Welcome, {user?.firstName || 'Teacher'} 👋</h1>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>

      <h2>Your Created Courses</h2>

      {loading && <p>Loading courses...</p>}
      {error && <p className="error-text">{error}</p>}
      {!loading && courses.length === 0 && <p>You haven't created any courses yet.</p>}

      <div className="courses-grid">
        {courses.map(course => (
          <div className="course-card" key={course.id}>
            <h3>{course.title}</h3>
            <p>{course.description}</p>
            <button className="manage-btn">Manage Course</button>
          </div>
        ))}
      </div>
    </div>
  );
}
