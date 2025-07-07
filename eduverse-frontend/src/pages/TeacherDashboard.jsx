import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import "../styles/TeacherDashboard.css";
import { motion } from "framer-motion";
import { Plus } from "lucide-react";

export default function TeacherDashboard() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Get current user from localStorage
  const user = JSON.parse(localStorage.getItem("user"));

  // Fetch courses created by the current teacher
  useEffect(() => {
    if (!user || user.role !== "TEACHER") {
      navigate("/login");
      return;
    }

    const fetchCourses = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/courses/teacher/${user.id}`
        );
        
        setCourses(res.data);
      } catch (err) {
        setError("Failed to load courses");
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, [user, navigate]);

  // Logout handler

  return (
    <div className="teacher-dashboard">
      <div className="dashboard-header">
        <motion.h1
          className="dashboard-title"
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          Welcome, {user?.firstName || "Teacher"} 👋
        </motion.h1>
      </div>

      <div className="create-course-container">
        <button
          className="create-course-btn"
          onClick={() => navigate("/teacher/create-course")}
        >
          <Plus size={18} /> Create New Course
        </button>
      </div>

      <h2 className="section-title">Your Created Courses</h2>

      {loading && <p className="loading">Loading courses...</p>}
      {error && <p className="error-text">{error}</p>}
      {!loading && courses.length === 0 && (
        <p className="no-courses">You haven't created any courses yet.</p>
      )}

      <div className="courses-grid">
        {courses.map((course) => (
          <motion.div
            className="course-card"
            key={course.id}
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.4 }}
          >
            <img
              className="course-image"
              src={course.thumbnailUrl || "https://via.placeholder.com/150"}
              alt="Course Thumbnail"
            />
            <div className="course-content">
              <h3>{course.title}</h3>
              <p>{course.description.slice(0, 100)}...</p>
              <p className="meta">
                Modules: {course.totalModules || 0} • Enrolled:{" "}
                {course.enrolledStudents || 0}
              </p>
              <Link to={`/manage-course/${course.id}`}>View Course</Link> |{" "}
              <Link to={`/manage-course/${course.id}/edit`}>Edit Course</Link> |{" "}
              <Link to={`/manage-course/${course.id}/modules`}>
                Manage Modules
              </Link>{" "}
              |{" "}
              <Link to={`/manage-course/${course.id}/add-module`}>
                Add Module
              </Link>
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
}
