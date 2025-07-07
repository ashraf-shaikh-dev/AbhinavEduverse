import React from 'react';
import '../styles/AboutPage.css';

// This component shows the "About" page of the Eduverse platform
export default function About() {
  return (
    <div className="about-page">
      <h1>About Eduverse</h1>
      
      {/* Introduction about the platform */}
      <p>
        <strong>Eduverse</strong> is a next-generation e-learning platform built to revolutionize the way students and teachers interact with digital education. 
        Whether you're a student looking to master new skills or a teacher wanting to deliver engaging, structured content — Eduverse has you covered.
      </p>

      {/* Mission section */}
      <h2>Our Mission</h2>
      <p>
        Our mission is to make quality education accessible, personalized, and interactive for everyone. 
        We aim to bridge the gap between passionate educators and eager learners across the globe.
      </p>

      {/* Key Features list */}
      <h2>Key Features</h2>
      <ul>
        <li data-emoji="📚">Course Creation and Management</li>
        <li data-emoji="🧩">Module-based Learning Structure</li>
        <li data-emoji="👨‍🏫">Teacher and Student Dashboards</li>
        <li data-emoji="📊">Progress Tracking and Analytics</li>
        <li data-emoji="🔒">Secure Login and Role Management</li>
        <li data-emoji="🌐">Seamless User Experience</li>
      </ul>

      {/* Tech stack used to build Eduverse */}
      <h2>Built With</h2>
      <ul>
        <li data-emoji="💻">Frontend: React.js</li>
        <li data-emoji="🖥️">Backend: Spring Boot (Java)</li>
        <li data-emoji="🗄️">Database: MySQL</li>
        <li data-emoji="🛠️">Tools: VS Code, Spring Tool Suite, GitHub</li>
      </ul>

      {/* Vision statement */}
      <h2>Vision</h2>
      <p>
        We envision a world where learning is not limited by geography, finances, or rigid schedules. 
        With Eduverse, we’re building a platform where knowledge flows freely and meaningfully between people.
      </p>

      {/* Footer credit */}
      <p className="credit">
        Made with ❤️ by <strong>Ashraf Shaikh</strong> and team.
      </p>
    </div>
  );
}
