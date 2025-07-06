import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "../styles/AddModuleForm.css";

export default function AddModuleForm({ onModuleAdded = () => {} }) {
  const { courseId } = useParams();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    title: "",
    content: "",
    videoUrl: "",
  });

  const [moduleCount, setModuleCount] = useState(0);

  useEffect(() => {
    if (!courseId) return;
    const fetchModules = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/modules/course/${courseId}`);
        setModuleCount(response.data.length);
      } catch (error) {
        console.error("Failed to fetch modules:", error);
      }
    };
    fetchModules();
  }, [courseId]);

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`http://localhost:8080/api/modules/create`, {
        ...formData,
        courseId,
        moduleOrder: moduleCount + 1,
      });

      alert("Module is added successfully");

      setFormData({ title: "", content: "", videoUrl: "" });
      onModuleAdded();

      navigate(-1); // go back to previous page
    } catch (err) {
      console.error("Failed to add module:", err);
      alert("Failed to add module");
    }
  };

  return (
    <form className="add-module-form" onSubmit={handleSubmit}>
      <input
        type="text"
        name="title"
        placeholder="Module Title"
        value={formData.title}
        onChange={handleChange}
        required
      />
      <textarea
        name="content"
        placeholder="Module Content"
        value={formData.content}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="videoUrl"
        placeholder="YouTube Video URL (optional)"
        value={formData.videoUrl}
        onChange={handleChange}
      />
      <button type="submit">Add Module</button>
    </form>
  );
}
