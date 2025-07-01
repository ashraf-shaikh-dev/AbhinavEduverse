import React, { useEffect, useState, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import ModuleList from "../components/ModuleList";
import axios from "axios";

export default function ModuleListPage() {
  const { courseId } = useParams();
  const [modules, setModules] = useState([]);
  const navigate = useNavigate();

  // ✅ Wrap fetchModules in useCallback to avoid ESLint warning
  const fetchModules = useCallback(async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/modules/course/${courseId}`);
      setModules(res.data);
    } catch {
      alert("Failed to fetch modules");
    }
  }, [courseId]);

  useEffect(() => {
    fetchModules();
  }, [fetchModules]); 

  return (
    <div className="module-list-page">
      <h2>Modules</h2>
      <ModuleList modules={modules} onDelete={fetchModules} />
      <button className="btn-back" onClick={() => navigate(-1)}>Back</button>
    </div>
  );
}
