import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { motion } from 'framer-motion';
import '../styles/CoursesPage.css';

export default function CoursesPage() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [search, setSearch] = useState('');

  // Fetch all courses on mount
  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const res = await axios.get('http://localhost:8080/api/courses/all');
        setCourses(res.data);
      } catch (err) {
        console.error(err);
        setError('Failed to fetch courses');
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  // Filtered courses based on search input
  const filteredCourses = courses.filter(course =>
    course.title.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="courses-page">
      <header className="courses-header">
        <h1>Explore Courses</h1>
        <input
          type="text"
          placeholder="Search courses..."
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
      </header>

      {loading && <p className="loading">Loading courses...</p>}
      {error && <p className="error-text">{error}</p>}

      <div className="courses-grid">
        {filteredCourses.map(course => (
          <motion.div
            className="course-card"
            key={course.id}
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.3 }}
          >
            <img
              className="course-thumb"
              src={course.thumbnailUrl || 'https://via.placeholder.com/300x180'}
              alt="Course Thumbnail"
            />
            <div className="course-info">
              <h3>{course.title}</h3>
              <p>{course.description.slice(0, 100)}...</p>
              <p className="meta">
                Modules: {course.totalModules ?? 0}
              </p>
              <button className="enroll-btn">View Details</button>
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
}
