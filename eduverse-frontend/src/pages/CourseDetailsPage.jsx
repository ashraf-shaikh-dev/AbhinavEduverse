import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import EnrollButton from '../components/EnrollmentButton';
import '../styles/CourseDetailsPage.css';

// This page shows detailed info about a specific course
export default function CourseDetailsPage() {
  const { courseId } = useParams();              // Get courseId from URL
  const parsedCourseId = parseInt(courseId);     // Convert to number
  const [course, setCourse] = useState(null);    // Store course data here
  const [loading, setLoading] = useState(true);  // Loading state
  const [error, setError] = useState(null);      // Store error message if any

  // Fetch course details when component loads or courseId changes
  useEffect(() => {
    const fetchCourse = async () => {
      try {
        // If courseId is invalid, skip API call
        if (!parsedCourseId) {
          console.error("courseId is undefined or invalid, aborting API call");
          return;
        }

        // Call backend API to get course data
        const res = await axios.get(`http://localhost:8080/api/courses/${parsedCourseId}`);

        let courseData = res.data;

        // Sometimes response might be a JSON string, so try parsing it
        if (typeof courseData === "string") {
          try {
            courseData = JSON.parse(courseData);
          } catch (e) {
            console.error("Failed to parse course JSON", e);
          }
        }

        console.log('Parsed course data:', courseData);
        setCourse(courseData); // Save course info in state
      } catch (err) {
        setError('Failed to load course details'); // Show error message if API fails
      } finally {
        setLoading(false); // Done loading in both success or error case
      }
    };

    fetchCourse();
  }, [parsedCourseId]);

  // Show loading message while fetching
  if (loading) return <p>Loading course details...</p>;

  // Show error message if fetch failed
  if (error) return <p>{error}</p>;

  // Show if no course found for given id
  if (!course) return <p>No course found</p>;

  // Render the course details and enroll button
  return (
    <div className="course-details-page">
      <h1>{course.title || 'No title available'}</h1>
      <img
        src={course.thumbnailUrl || 'https://dummyimage.com/600x300/cccccc/000000&text=Course+Image'}
        alt={course.title || 'Course Image'}
      />
      <pre className="course-description">{course.description || 'No description available'}</pre>
      <p>Total Modules: {course.totalModules ?? 0}</p>

      {/* Enrollment button to enroll in this course */}
      <EnrollButton courseId={parsedCourseId} />
    </div>
  );
}
