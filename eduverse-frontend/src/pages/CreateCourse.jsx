import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/CreateCourse.css';  // Import external CSS

export default function CreateCourse() {
  // State to hold form inputs
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    thumbnailUrl: '',
    teacherId: ''
  });

  // State for loading and error messages
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const navigate = useNavigate();

  // On mount, get teacherId from logged-in user stored in localStorage
  useEffect(() => {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user?.id) {
        setFormData(prev => ({ ...prev, teacherId: user.id }));
      }
    }
  }, []);

  // Update state when input fields change
  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  // Handle form submission to backend API
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      // Convert teacherId to number before sending
      const payload = { ...formData, teacherId: Number(formData.teacherId) };
      await axios.post('http://localhost:8080/api/courses/create', payload);
      alert('Course created successfully!');
      navigate('/teacher/dashboard');
    } catch (err) {
      // Show backend error message or generic message
      setError(err.response?.data?.message || 'Failed to create course');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="create-course-container">
      <h2>Create New Course</h2>
      <form onSubmit={handleSubmit} className="create-course-form">

        <label htmlFor="title">Course Title</label>
        <input
          id="title"
          name="title"
          type="text"
          value={formData.title}
          onChange={handleChange}
          required
          placeholder="Enter course title"
        />

        <label htmlFor="description">Course Description</label>
        <textarea
          id="description"
          name="description"
          value={formData.description}
          onChange={handleChange}
          required
          placeholder="Enter course description"
        />

        <label htmlFor="thumbnailUrl">Thumbnail URL (optional)</label>
        <input
          id="thumbnailUrl"
          name="thumbnailUrl"
          type="text"
          value={formData.thumbnailUrl}
          onChange={handleChange}
          placeholder="Enter thumbnail image URL"
        />

        {/* Hidden teacherId field */}
        <input
          name="teacherId"
          type="hidden"
          value={formData.teacherId}
          readOnly
        />

        {/* Display error message if exists */}
        {error && <p className="error-text">{error}</p>}

        {/* Submit button disabled during loading */}
        <button type="submit" disabled={loading} className="submit-btn">
          {loading ? 'Creating...' : 'Create Course'}
        </button>
      </form>
    </div>
  );
}
