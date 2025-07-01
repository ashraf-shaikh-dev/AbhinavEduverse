import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/CourseInfoPage.css";

export default function CourseInfoPage() {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const [course, setCourse] = useState(null);
  const [modules, setModules] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const [courseRes, modulesRes] = await Promise.all([
          axios.get(`http://localhost:8080/api/courses/${courseId}`),
          axios.get(`http://localhost:8080/api/modules/course/${courseId}`)
        ]);
        setCourse(courseRes.data);
        setModules(modulesRes.data);
      } catch (error) {
        console.error("Failed to fetch course or modules:", error);
      } finally {
        setLoading(false);
      }
    }
    fetchData();
  }, [courseId]);

  if (loading) return <p className="loading">Loading course details...</p>;
  if (!course) return <p className="error">Course not found.</p>;

  return (
    <div className="course-info-container">
      {course.thumbnailUrl ? (
        <img
          src={course.thumbnailUrl}
          alt="Course Thumbnail"
          className="course-thumbnail"
          onError={(e) =>
            (e.target.src = "https://via.placeholder.com/600x300?text=No+Image")
          }
        />
      ) : (
        <div className="no-thumbnail">No thumbnail available</div>
      )}

      <div className="course-details">
        <h1>{course.title}</h1>
        <p>{course.description}</p>
        <p>
          <strong>Modules:</strong> {modules.length}
        </p>
        <button className="btn-edit" onClick={() => navigate("edit")}>
          Edit Course Info
        </button>
        <button className="btn-add-module" onClick={() => navigate("add-module")}>
          Add Module
        </button>
        <button className="btn-view-modules" onClick={() => navigate("modules")}>
          View Modules
        </button>
      </div>
    </div>
  );
}
