import React from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import '../styles/LandingPage.css';
import expertImg from '../assets/expert.png';
import flexibility from '../assets/flexibility.png';
import certificate from '../assets/certificate.png';

export default function LandingPage() {
  const navigate = useNavigate();

  // When user clicks "Get Started" button, go to courses page
  const handleGetStarted = () => {
    navigate('/courses');
  };

  // When user clicks "Join Now" button, go to signup page
  const handleJoinNow = () => {
    navigate('/signup');
  };

  return (
    <div className="landing-page">

      {/* Hero section with title, subtitle, and button */}
      <div className="hero">
        <motion.div
          className="overlay"
          initial={{ opacity: 0 }}        // Start invisible
          animate={{ opacity: 1 }}        // Fade in to visible
          transition={{ duration: 1 }}    // Take 1 second to fade
        >
          <h1 className="hero-title">Learn from the best. Anytime. Anywhere.</h1>
          <p className="hero-subtitle">
            Join thousands of learners and instructors around the world.
          </p>
          <button className="hero-btn" onClick={handleGetStarted}>
            Get Started
          </button>
        </motion.div>
      </div>

      {/* Features section highlighting platform benefits */}
      <section className="features-section">
        <h2>Why Choose Abhinav Eduverse?</h2>
        <div className="features">
          <div className="feature-card">
            <img src={expertImg} alt="Expert Instructors" />
            <h3>Expert Instructors</h3>
            <p>Learn from industry professionals with hands-on experience.</p>
          </div>
          <div className="feature-card">
            <img src={flexibility} alt="Flexible Learning" />
            <h3>Flexible Learning</h3>
            <p>Study at your own pace on any device, anytime, anywhere.</p>
          </div>
          <div className="feature-card">
            <img src={certificate} alt="Certifications" />
            <h3>Certifications</h3>
            <p>Earn certificates to boost your career and showcase your skills.</p>
          </div>
        </div>
      </section>

      {/* Call to Action (CTA) section encouraging users to sign up */}
      <section className="cta-section">
        <h2>Start Your Learning Journey Today</h2>
        <p>
          Whether you want to learn something new or teach others, Eduverse has a place for you.
        </p>
        <button className="cta-btn" onClick={handleJoinNow}>Join Now</button>
      </section>
    </div>
  );
}
