import React, { useState } from 'react';
import axios from 'axios'; // For making API calls to the Spring Boot backend
import '../styles/global.css'; // Import your CSS styles

export default function SignupPage() {
  // useState hook to manage form input values
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    role: 'STUDENT' // Default role
  });

  // This function updates form state on input change
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value // Dynamically update the field
    });
  };

  // This function handles form submission
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent page reload
    try {
      // Send POST request to backend signup API
      await axios.post('http://localhost:8080/api/users/signup', formData);
      alert('Signup successful!');
    } catch (err) {
      alert('Signup failed!');
      console.error('Signup error:', err);
    }
  };

  return (
    <div className="container">
      <h2>Signup</h2>
      <form onSubmit={handleSubmit}>
        {/* Input fields for user details */}
        <input
          name="firstName"
          placeholder="First Name"
          value={formData.firstName}
          onChange={handleChange}
        />
        <input
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
        />
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
        
        {/* Role selector dropdown */}
        <select
          name="role"
          value={formData.role}
          onChange={handleChange}
        >
          <option value="STUDENT">Student</option>
          <option value="TEACHER">Teacher</option>
        </select>

        {/* Submit button */}
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}
