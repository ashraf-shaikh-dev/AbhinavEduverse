import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/CreateCourse.css';  // Import CSS for styling

export default function CreateCourse() {
  // Form data state for course info
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    thumbnailUrl: '',
    teacherId: ''
  });

  // Loading state to disable button while submitting
  const [loading, setLoading] = useState(false);
  // Error message state to show backend errors
  const [error, setError] = useState('');

  const navigate = useNavigate();

  // On component mount, get logged-in user and set teacherId
  useEffect(() => {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user?.id) {
        // Set teacherId in form data from logged-in user
        setFormData(prev => ({ ...prev, teacherId: user.id }));
      }
    }
  }, []);

  // Update form data on input changes
  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  // Handle form submit: call backend API to create course
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);  // Start loading
    setError('');      // Clear previous error

    try {
      // Ensure teacherId is a number before sending
      const payload = { ...formData, teacherId: Number(formData.teacherId) };
      await axios.post('http://localhost:8080/api/courses/create', payload);
      alert('Course created successfully!');
      navigate('/teacher/dashboard'); // Redirect to dashboard after success
    } catch (err) {
      // Show error from backend if available, else generic error
      setError(err.response?.data?.message || 'Failed to create course');
    } finally {
      setLoading(false); // Stop loading
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

        {/* Hidden input to keep teacherId */}
        <input
          name="teacherId"
          type="hidden"
          value={formData.teacherId}
          readOnly
        />

        {/* Show error message if any */}
        {error && <p className="error-text">{error}</p>}

        {/* Submit button disables during loading */}
        <button type="submit" disabled={loading} className="submit-btn">
          {loading ? 'Creating...' : 'Create Course'}
        </button>
      </form>
    </div>
  );
}
