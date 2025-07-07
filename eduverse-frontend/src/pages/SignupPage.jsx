import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import '../styles/global.css';

export default function SignupPage() {
  // State to hold form inputs
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    role: 'STUDENT', // Default role is STUDENT
  });

  // State to hold error message
  const [error, setError] = useState('');
  const navigate = useNavigate(); // To redirect after signup

  // Update form state when user types in input fields
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value, // Update the changed field
    });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(''); // Clear any previous error
    try {
      // Send signup data to backend API
      await axios.post('http://localhost:8080/api/users/signup', formData);
      alert('Signup successful!');
      navigate('/login'); // Redirect to login page on success
    } catch (err) {
      console.error('Signup error:', err);
      // Show error message from backend or generic one
      if (err.response?.data?.message) {
        setError(err.response.data.message);
      } else {
        setError('Signup failed. Please try again.');
      }
    }
  };

  return (
    <div className="container">
      <h2>Signup</h2>

      {/* Show error message if there is one */}
      {error && <p className="error-text" role="alert">{error}</p>}

      <form onSubmit={handleSubmit} noValidate>
        {/* Input fields with controlled values */}
        <input
          name="firstName"
          placeholder="First Name"
          value={formData.firstName}
          onChange={handleChange}
          required
          aria-label="First Name"
        />
        <input
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
          required
          aria-label="Last Name"
        />
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
          aria-label="Email"
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
          aria-label="Password"
        />

        {/* Dropdown to select role */}
        <select
          name="role"
          value={formData.role}
          onChange={handleChange}
          aria-label="Role"
        >
          <option value="STUDENT">Student</option>
          <option value="TEACHER">Teacher</option>
        </select>

        {/* Submit button */}
        <button type="submit">Sign Up</button>
      </form>

      {/* Link to login page */}
      <p style={{ marginTop: '1rem' }}>
        Already have an account? <Link to="/login">Login here</Link>
      </p>
    </div>
  );
}
