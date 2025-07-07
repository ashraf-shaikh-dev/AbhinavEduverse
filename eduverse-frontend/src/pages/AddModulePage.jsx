import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "../styles/AddModuleForm.css";

// This component shows a form to add a new module to a course
export default function AddModuleForm({ onModuleAdded = () => {} }) {
  const { courseId } = useParams(); // Get courseId from URL
  const navigate = useNavigate();   // For navigating programmatically

  // State for form inputs
  const [formData, setFormData] = useState({
    title: "",
    content: "",
    videoUrl: "",
  });

  // State to track how many modules are already in this course
  const [moduleCount, setModuleCount] = useState(0);

  // Fetch existing modules count when component loads or courseId changes
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

  // Update form state when input values change
  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  // When user submits the form
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent page reload

    try {
      // Send data to backend to create the new module
      await axios.post(`http://localhost:8080/api/modules/create`, {
        ...formData,
        courseId,
        moduleOrder: moduleCount + 1, // Place new module at the end
      });

      alert("Module is added successfully");

      setFormData({ title: "", content: "", videoUrl: "" }); // Clear form

      onModuleAdded(); // Tell parent to refresh module list

      navigate(-1); // Go back to previous page
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
