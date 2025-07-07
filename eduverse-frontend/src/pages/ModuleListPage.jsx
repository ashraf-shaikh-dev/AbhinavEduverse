import React, { useEffect, useState, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import ModuleList from "../components/ModuleList";
import axios from "axios";

export default function ModuleListPage() {
  const { courseId } = useParams(); // Get courseId from URL parameters
  const [modules, setModules] = useState([]); // Store list of modules here
  const navigate = useNavigate(); // For navigation (going back etc)

  // Function to fetch modules for the given course from backend
  // useCallback so useEffect only runs when courseId changes
  const fetchModules = useCallback(async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/modules/course/${courseId}`);
      setModules(res.data); // Save fetched modules in state
    } catch {
      alert("Failed to fetch modules"); // Show error if request fails
    }
  }, [courseId]); // Dependency - refetch if courseId changes

  // Run fetchModules once when component mounts or courseId changes
  useEffect(() => {
    fetchModules();
  }, [fetchModules]); 

  return (
    <div className="module-list-page">
      <h2>Modules</h2>
      {/* Pass modules and refresh function to ModuleList component */}
      <ModuleList modules={modules} onDelete={fetchModules} />
      
      {/* Back button to go to previous page */}
      <button className="btn-back" onClick={() => navigate(-1)}>Back</button>
    </div>
  );
}
