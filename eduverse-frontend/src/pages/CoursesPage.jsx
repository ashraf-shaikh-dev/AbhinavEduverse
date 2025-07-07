import React, { useEffect, useState } from "react";
import axios from "axios";
import { motion } from "framer-motion";
import "../styles/CoursesPage.css";
import { useNavigate } from "react-router-dom";
import AOS from "aos";
import "aos/dist/aos.css";

export default function CoursesPage() {
  const [courses, setCourses] = useState([]); // Store all courses here
  const [loading, setLoading] = useState(true); // Loading state
  const [error, setError] = useState(null); // Error message
  const [search, setSearch] = useState(""); // Search input state
  const navigate = useNavigate(); // For navigation

  // Fetch courses when component mounts
  useEffect(() => {
    // Initialize AOS animations on scroll
    AOS.init({ duration: 800, once: true });

    const fetchCourses = async () => {
      try {
        // Get all courses from backend API
        const res = await axios.get("http://localhost:8080/api/courses/all");
        setCourses(res.data); // Save courses to state
      } catch (err) {
        console.error(err);
        setError("Failed to fetch courses"); // Show error if fetch fails
      } finally {
        setLoading(false); // Stop loading spinner
      }
    };

    fetchCourses();
  }, []);

  // Filter courses based on search input (case insensitive)
  const filteredCourses = courses.filter((course) =>
    course.title.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="courses-page">
      {/* Header with title and search box */}
      <header className="courses-header">
        <h1>Explore Courses</h1>
        <input
          type="text"
          placeholder="Search courses..."
          value={search}
          onChange={(e) => setSearch(e.target.value)} // Update search state
        />
      </header>

      {/* Show loading or error messages */}
      {loading && <p className="loading">Loading courses...</p>}
      {error && <p className="error-text">{error}</p>}

      {/* Grid showing all filtered courses */}
      <div className="courses-grid">
        {filteredCourses.map((course) => (
          <motion.div
            className="course-card"
            key={course.id}
            initial={{ opacity: 0, scale: 0.95 }} // Animation start state
            animate={{ opacity: 1, scale: 1 }} // Animation end state
            transition={{ duration: 0.3 }} // Animation duration
            data-aos="fade-up" // AOS animation on scroll
          >
            {/* Course thumbnail */}
            <img
              className="course-thumb"
              src={course.thumbnailUrl || "https://via.placeholder.com/300x180"}
              alt="Course Thumbnail"
            />
            <div className="course-info">
              <h3>{course.title}</h3>
              {/* Show first 100 chars of description */}
              <p>{course.description.slice(0, 100)}...</p>
              <p className="meta">Modules: {course.totalModules ?? 0}</p>

              {/* Button to navigate to course detail page */}
              <button
                className="enroll-btn"
                onClick={() => navigate(`/courses/${course.id}`)}
              >
                View Details
              </button>
            </div>
          </motion.div>
        ))}
        {/* Show message if no courses found */}
        {!loading && filteredCourses.length === 0 && (
          <p>No courses found matching "{search}"</p>
        )}
      </div>
    </div>
  );
}
