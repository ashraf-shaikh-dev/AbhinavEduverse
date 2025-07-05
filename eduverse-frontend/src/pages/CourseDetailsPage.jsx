import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import EnrollButton from '../components/EnrollmentButton';
import '../styles/CourseDetailsPage.css';

export default function CourseDetailsPage() {
  const { courseId } = useParams();
  const parsedCourseId = parseInt(courseId);
  const [course, setCourse] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

useEffect(() => {
  const fetchCourse = async () => {
    try {
      if (!parsedCourseId) {
        console.error("courseId is undefined or invalid, aborting API call");
        return;
      }

      const res = await axios.get(`http://localhost:8080/api/courses/${parsedCourseId}`);

      let courseData = res.data;
      if (typeof courseData === "string") {
        try {
          courseData = JSON.parse(courseData);
        } catch (e) {
          console.error("Failed to parse course JSON", e);
        }
      }

      console.log('Parsed course data:', courseData);
      setCourse(courseData);
    } catch (err) {
      setError('Failed to load course details');
    } finally {
      setLoading(false);
    }
  };

  fetchCourse();
}, [parsedCourseId]);


  if (loading) return <p>Loading course details...</p>;
  if (error) return <p>{error}</p>;
  if (!course) return <p>No course found</p>;

  return (
    <div className="course-details-page">
      <h1>{course.title || 'No title available'}</h1>
      <img
        src={course.thumbnailUrl || 'https://dummyimage.com/600x300/cccccc/000000&text=Course+Image'}
        alt={course.title || 'Course Image'}
      />
      <pre className="course-description">{course.description || 'No description available'}</pre>
      <p>Total Modules: {course.totalModules ?? 0}</p>

      <EnrollButton courseId={parsedCourseId} />

      
    </div>
  );
}
