import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import EnrollButton from '../components/EnrollmentButton';
import '../styles/CourseDetailsPage.css'

export default function CourseDetailsPage() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCourse = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/courses/${courseId}`);
        setCourse(res.data);
      } catch (err) {
        setError('Failed to load course details');
      } finally {
        setLoading(false);
      }
    };

    fetchCourse();
  }, [courseId]);

  if (loading) return <p>Loading course details...</p>;
  if (error) return <p>{error}</p>;
  if (!course) return <p>No course found</p>;

  return (
    <div className="course-details-page">
      <h1>{course.title}</h1>
      <img src={course.thumbnailUrl || 'https://via.placeholder.com/600x300'} alt={course.title} />
      <p>{course.description}</p>
      <p>Total Modules: {course.totalModules ?? 0}</p>

      {/* Enroll button here */}
      <EnrollButton courseId={course.id} />
    </div>
  );
}
