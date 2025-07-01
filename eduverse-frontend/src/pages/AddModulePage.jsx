import React, { useState } from "react";
import axios from "axios";
import "../styles/AddModuleForm.css";

export default function AddModuleForm({ courseId, onModuleAdded }) {
  const [formData, setFormData] = useState({
    title: "",
    content: "",
    videoUrl: "",
  });

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`http://localhost:8080/api/modules/create`, {
        ...formData,
        courseId,
      });
      setFormData({ title: "", content: "", videoUrl: "" });
      onModuleAdded();
    } catch {
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
