import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AddModuleForm.css';

// This component is used to add a new module to a course
export default function AddModuleForm({ courseId, onModuleAdded }) {
  // Initial state for the form inputs
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    videoUrl: '',
    moduleOrder: '', // (optional) if we want to set order of modules
  });

  // This function updates form data when we type something in the inputs
  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  // This function runs when we click the "Add Module" button
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevents page from refreshing
    try {
      // Sending the form data to backend API to save the module
      await axios.post(`http://localhost:8080/api/modules/create`, {
        ...formData,
        courseId: courseId // attach the courseId to link it correctly
      });

      // Reset the form after adding
      setFormData({ title: '', content: '', videoUrl: '', moduleOrder: '' });

      // Tells the parent component to refresh the module list
      onModuleAdded();
    } catch (err) {
      alert('Failed to add module'); // Show error if something goes wrong
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
      ></textarea>
      <input
        type="text"
        name="videoUrl"
        placeholder="Video URL"
        value={formData.videoUrl}
        onChange={handleChange}
      />
      <button type="submit">Add Module</button>
    </form>
  );
}
