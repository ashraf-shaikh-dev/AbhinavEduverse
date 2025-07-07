// src/pages/ManageCourseLayout.jsx
import React, { useEffect, useState, useCallback } from "react";
import { Outlet, useParams } from "react-router-dom";
import axios from "axios";
import "../styles/ManageCourse.css";

export default function ManageCourseLayout() {
  const { courseId } = useParams(); // Get courseId from URL params
  const [course, setCourse] = useState(null); // Store course details here
  const [modules, setModules] = useState([]); // Store course modules here
  const [loading, setLoading] = useState(true); // Loading state
  const [error, setError] = useState(null); // Error state

  // Function to fetch course and modules data from backend
  // useCallback used to avoid infinite loops in useEffect
  const fetchCourseData = useCallback(async () => {
    try {
      // Fetch course details
      const res = await axios.get(`http://localhost:8080/api/courses/${courseId}`);
      setCourse(res.data);

      // Fetch modules for the course
      const moduleRes = await axios.get(`http://localhost:8080/api/modules/course/${courseId}`);
      setModules(moduleRes.data);

      setError(null); // Clear error if successful
    } catch (err) {
      console.error("Failed to fetch course or modules", err);
      setError("Failed to fetch course or modules."); // Set error message
    } finally {
      setLoading(false); // Stop loading in both success and error cases
    }
  }, [courseId]); // Dependency array - refetch if courseId changes

  // Fetch data when component mounts or courseId changes
  useEffect(() => {
    fetchCourseData();
  }, [fetchCourseData]);

  // Show loading text while fetching
  if (loading) return <p>Loading course...</p>;

  // Show error message if fetch failed
  if (error) return <p className="error-text">{error}</p>;

  // Render child routes and pass course, modules and refresh function
  return (
    <div className="manage-course-layout">
      <Outlet context={{ course, modules, refreshModules: fetchCourseData }} />
    </div>
  );
}
