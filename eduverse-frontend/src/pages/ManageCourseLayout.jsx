// src/pages/ManageCourseLayout.jsx
import React, { useEffect, useState, useCallback } from "react";
import { Outlet, useParams } from "react-router-dom";
import axios from "axios";
import "../styles/ManageCourse.css";

export default function ManageCourseLayout() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [modules, setModules] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // ✅ Define fetchCourseData using useCallback to avoid dependency warnings
  const fetchCourseData = useCallback(async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/courses/${courseId}`);
      setCourse(res.data);

      const moduleRes = await axios.get(`http://localhost:8080/api/modules/course/${courseId}`);
      setModules(moduleRes.data);

      setError(null);
    } catch (err) {
      console.error("Failed to fetch course or modules", err);
      setError("Failed to fetch course or modules.");
    } finally {
      setLoading(false);
    }
  }, [courseId]);

  useEffect(() => {
    fetchCourseData();
  }, [fetchCourseData]);

  if (loading) return <p>Loading course...</p>;
  if (error) return <p className="error-text">{error}</p>;

  return (
    <div className="manage-course-layout">
      {/* ✅ Share course + modules + refresh function via Outlet context */}
      <Outlet context={{ course, modules, refreshModules: fetchCourseData }} />
    </div>
  );
}
