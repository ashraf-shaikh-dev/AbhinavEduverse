import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import "../styles/TeacherDashboard.css";
import { motion } from "framer-motion";
import { Plus } from "lucide-react";

export default function TeacherDashboard() {
  // State to hold list of courses created by this teacher
  const [courses, setCourses] = useState([]);
  // Loading and error states
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Get the logged-in user info from localStorage
  const user = JSON.parse(localStorage.getItem("user"));

  // When component mounts, fetch courses for this teacher
  useEffect(() => {
    // If user not logged in or not a teacher, redirect to login page
    if (!user || user.role !== "TEACHER") {
      navigate("/login");
      return;
    }

    // Async function to fetch teacher's courses from backend
    const fetchCourses = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/courses/teacher/${user.id}`
        );
        setCourses(res.data); // Save courses to state
      } catch (err) {
        setError("Failed to load courses"); // Show error message on failure
      } finally {
        setLoading(false); // Loading done either way
      }
    };

    fetchCourses();
  }, [user, navigate]);

  // UI rendering
  return (
    <div className="teacher-dashboard">
      <div className="dashboard-header">
        {/* Animated greeting with teacher's first name or fallback */}
        <motion.h1
          className="dashboard-title"
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          Welcome, {user?.firstName || "Teacher"} 👋
        </motion.h1>
      </div>

      {/* Button to create a new course */}
      <div className="create-course-container">
        <button
          className="create-course-btn"
          onClick={() => navigate("/teacher/create-course")}
        >
          <Plus size={18} /> Create New Course
        </button>
      </div>

      <h2 className="section-title">Your Created Courses</h2>

      {/* Show loading or error messages */}
      {loading && <p className="loading">Loading courses...</p>}
      {error && <p className="error-text">{error}</p>}

      {/* Show message if no courses are created */}
      {!loading && courses.length === 0 && (
        <p className="no-courses">You haven't created any courses yet.</p>
      )}

      {/* List of teacher's courses */}
      <div className="courses-grid">
        {courses.map((course) => (
          <motion.div
            className="course-card"
            key={course.id}
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.4 }}
          >
            {/* Course thumbnail */}
            <img
              className="course-image"
              src={course.thumbnailUrl || "https://via.placeholder.com/150"}
              alt="Course Thumbnail"
            />
            <div className="course-content">
              {/* Course title */}
              <h3>{course.title}</h3>
              {/* Short course description preview */}
              <p>{course.description.slice(0, 100)}...</p>
              {/* Meta info: module count and enrolled students */}
              <p className="meta">
                Modules: {course.totalModules || 0} • Enrolled:{" "}
                {course.enrolledStudents || 0}
              </p>
              {/* Links to manage course, edit, modules, and add module */}
              <Link to={`/manage-course/${course.id}`}>View Course</Link> |{" "}
              <Link to={`/manage-course/${course.id}/edit`}>Edit Course</Link> |{" "}
              <Link to={`/manage-course/${course.id}/modules`}>Manage Modules</Link>{" "}
              |{" "}
              <Link to={`/manage-course/${course.id}/add-module`}>Add Module</Link>
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
}
