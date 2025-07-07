import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext.jsx';
import { useNavigate } from 'react-router-dom';

// This component shows an "Enroll" button for students
export default function EnrollButton({ courseId }) {
  const { user, isLoggedIn } = useAuth(); // Get logged-in user info
  const navigate = useNavigate();

  const [isEnrolled, setIsEnrolled] = useState(false); // Whether the user is already enrolled
  const [loading, setLoading] = useState(false);       // Loading state while enrolling
  const [checkingStatus, setCheckingStatus] = useState(true); // While checking enrollment

  // If user is not logged in or not a student, redirect to login
  useEffect(() => {
    if (!isLoggedIn || user?.role !== 'STUDENT') {
      navigate('/login');
      return;
    }
  }, [isLoggedIn, user, navigate]);

  // Check if the student is already enrolled in this course
  useEffect(() => {
    if (!courseId || !user?.id || !isLoggedIn) {
      setCheckingStatus(false);
      return;
    }

    const checkEnrollment = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/enrollments/course/${courseId}`);
        const enrolledStudentIds = res.data.map(student => student.id);
        if (enrolledStudentIds.includes(user.id)) {
          setIsEnrolled(true);
        }
      } catch (err) {
        console.error("Enrollment check failed", err);
      } finally {
        setCheckingStatus(false); // Done checking
      }
    };

    checkEnrollment();
  }, [courseId, user, isLoggedIn]);

  // Function to enroll in the course
  const handleEnroll = async () => {
    setLoading(true); // Start loading
    try {
      await axios.post('http://localhost:8080/api/enrollments/enroll', {
        studentId: user.id,
        courseId: courseId
      });
      alert('Successfully enrolled!');
      setIsEnrolled(true); // Update state after enrolling
    } catch (error) {
      alert(error.response?.data || 'Enrollment failed');
    } finally {
      setLoading(false); // Stop loading
    }
  };

  // If user is not a student, don’t show anything
  if (!isLoggedIn || user?.role !== 'STUDENT') return null;

  // While checking enrollment status
  if (checkingStatus) return <button disabled className="btn btn-light">Checking...</button>;

  // If already enrolled, show "Go to Course" button
  if (isEnrolled) {
    return (
      <button
        className="btn btn-success"
        onClick={() => navigate(`/course-modules/${courseId}`)}
      >
        Go to Course
      </button>
    );
  }

  // If not enrolled, show "Enroll" button
  return (
    <button
      onClick={handleEnroll}
      disabled={loading}
      className="btn btn-primary"
    >
      {loading ? 'Enrolling...' : 'Enroll in this Course'}
    </button>
  );
}
