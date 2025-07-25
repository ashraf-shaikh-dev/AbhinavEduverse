import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/CourseInfoPage.css";

// This page shows course details and lets you navigate to edit or manage modules
export default function CourseInfoPage() {
  const { courseId } = useParams(); // Get courseId from URL
  const navigate = useNavigate();   // For navigation buttons
  const [course, setCourse] = useState(null);    // Store course info here
  const [modules, setModules] = useState([]);    // Store modules of this course
  const [loading, setLoading] = useState(true);  // Loading state

  // Fetch course and modules data when page loads or courseId changes
  useEffect(() => {
    async function fetchData() {
      try {
        // Make both API calls in parallel for better performance
        const [courseRes, modulesRes] = await Promise.all([
          axios.get(`http://localhost:8080/api/courses/${courseId}`),
          axios.get(`http://localhost:8080/api/modules/course/${courseId}`),
        ]);
        setCourse(courseRes.data);   // Save course info
        setModules(modulesRes.data); // Save list of modules
      } catch (error) {
        console.error("Failed to fetch course or modules:", error);
      } finally {
        setLoading(false); // Finished loading no matter success or fail
      }
    }
    fetchData();
  }, [courseId]);

  // Show loading message while fetching data
  if (loading) return <p className="loading">Loading course details...</p>;

  // Show error if course is not found
  if (!course) return <p className="error">Course not found.</p>;

  return (
    <div className="course-info-container">
      {/* Show course thumbnail or placeholder if missing */}
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
        {/* Show description with line breaks */}
        <p className="course-description">
          {course.description.split("\n").map((line, index) => (
            <React.Fragment key={index}>
              {line}
              <br />
            </React.Fragment>
          ))}
        </p>

        <p>
          <strong>Modules:</strong> {modules.length}
        </p>

        {/* Buttons to navigate to edit course info, add module, or view modules */}
        <button className="btn-edit" onClick={() => navigate("edit")}>
          Edit Course Info
        </button>
        <button
          className="btn-add-module"
          onClick={() => navigate("add-module")}
        >
          Add Module
        </button>
        <button
          className="btn-view-modules"
          onClick={() => navigate("modules")}
        >
          View Modules
        </button>
      </div>
    </div>
  );
}
