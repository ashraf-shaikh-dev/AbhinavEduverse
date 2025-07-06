import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext.jsx';
import { useNavigate } from 'react-router-dom';

export default function EnrollButton({ courseId }) {
  const { user, isLoggedIn } = useAuth();
  const navigate = useNavigate();

  const [isEnrolled, setIsEnrolled] = useState(false);
  const [loading, setLoading] = useState(false);
  const [checkingStatus, setCheckingStatus] = useState(true);

  useEffect(() => {
    if (!isLoggedIn || user?.role !== 'STUDENT') {
      navigate('/login');
      return;
    }
  }, [isLoggedIn, user, navigate]);

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
        setCheckingStatus(false);
      }
    };

    checkEnrollment();
  }, [courseId, user, isLoggedIn]);

  const handleEnroll = async () => {
    setLoading(true);
    try {
      await axios.post('http://localhost:8080/api/enrollments/enroll', {
        studentId: user.id,
        courseId: courseId
      });
      alert('Successfully enrolled!');
      setIsEnrolled(true);
    } catch (error) {
      alert(error.response?.data || 'Enrollment failed');
    } finally {
      setLoading(false);
    }
  };

  if (!isLoggedIn || user?.role !== 'STUDENT') return null;

  if (checkingStatus) return <button disabled className="btn btn-light">Checking...</button>;

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
