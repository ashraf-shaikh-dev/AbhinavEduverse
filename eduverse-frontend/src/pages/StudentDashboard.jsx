import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
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

    const fetchEnrolledCourses = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/enrollments/student/${user.id}/courses`);
        setCourses(res.data); // Array of EnrolledCourseDTO
      } catch (err) {
        console.error('Error fetching enrolled courses:', err);
        setError('Failed to load enrolled courses.');
      } finally {
        setLoading(false);
      }
    };

    fetchEnrolledCourses();
  }, [user, navigate]);

  const handleContinueCourse = (courseId) => {
    navigate(`/courses/${courseId}`);
  };
  

  return (
    <div className="courses-page">
      <div className="courses-header">
        <h1>Welcome, {user?.firstName || 'Student'} 👋</h1>
        
      </div>

      <p className="meta">Your enrolled courses:</p>

      {loading && <p className="meta">Loading...</p>}
      {error && <p className="meta" style={{ color: 'red' }}>{error}</p>}
      {!loading && !error && courses.length === 0 && (
        <p className="meta">You are not enrolled in any courses yet.</p>
      )}

      <div className="courses-grid">
        {courses.map(course => (
          <div
            className="course-card"
            key={course.courseId}
            onClick={() => handleContinueCourse(course.courseId)}
          >
            <img
              src={course.thumbnailUrl || 'https://via.placeholder.com/300x180'}
              alt={course.title}
              className="course-thumb"
            />
            <div className="course-info">
              <h3>{course.title}</h3>
              <p className="meta">
                {course.description?.slice(0, 80) || 'No description'}...
              </p>
              <p className="meta">
                Progress: {course.completedModules} / {course.totalModules} modules
              </p>
              <p className="meta">
                Completion: {course.progress?.toFixed(1) || 0}% complete
              </p>
              <button
                className="enroll-btn"
                onClick={(e) => {
                  e.stopPropagation();
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
