import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx'; // Custom auth context hook
import '../styles/LoginPage.css';

export default function LoginPage() {
  const { login } = useAuth(); // Get login function from AuthContext
  const navigate = useNavigate(); // Used to redirect user after login

  // State for email and password input fields
  const [formData, setFormData] = useState({ email: '', password: '' });
  // State to show any error messages
  const [error, setError] = useState('');

  // Update form data as user types
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // When the form is submitted
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent page reload
    setError(''); // Clear previous errors
    try {
      // Call backend API to login user
      const res = await axios.post('http://localhost:8080/api/users/login', formData);
      const userData = res.data;
      login(userData);  // Save user info in context and localStorage

      // Redirect user based on role
      if (userData.role === 'TEACHER') {
        navigate('/teacher/dashboard');
      } else {
        navigate('/student/dashboard');
      }
    } catch (err) {
      // Show error from server or generic message
      setError(err.response?.data?.message || 'Login failed. Please check credentials.');
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      {/* Show error message if login fails */}
      {error && <p className="error-text">{error}</p>}

      {/* Login form */}
      <form onSubmit={handleSubmit} noValidate>
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>

      {/* Link to signup page */}
      <p style={{ marginTop: '1rem' }}>
        Don't have an account? <Link to="/signup">Sign up here</Link>
      </p>
    </div>
  );
}
