import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // To redirect after login
import '../styles/global.css';

export default function LoginPage() {
  const [formData, setFormData] = useState({
    email: '', password: ''
  });

  const navigate = useNavigate();

  // Handle form field updates
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  // Handle form submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Send POST request to backend
      const res = await axios.post('http://localhost:8080/api/users/login', formData);
      const user = res.data;

      alert('Login successful!');

      // Store user info in localStorage (simulate session)
      localStorage.setItem('user', JSON.stringify(user));

      // Redirect to dashboard based on role
      if (user.role === 'TEACHER') {
        navigate('/teacher/dashboard');
      } else {
        navigate('/student/dashboard');
      }
    } catch (err) {
      alert('Login failed!');
      console.error('Login error:', err);
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}
