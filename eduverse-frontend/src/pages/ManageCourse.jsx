import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import AddModuleForm from '../components/AddModuleForm';
import ModuleList from '../components/ModuleList';
import '../styles/ManageCourse.css';

export default function ManageCourse() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [modules, setModules] = useState([]);
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [thumbnail, setThumbnail] = useState('');
  const [showModuleForm, setShowModuleForm] = useState(false);

  const fetchCourse = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/courses/${courseId}`);
      setCourse(res.data);
    } catch (err) {
      setError('Failed to fetch course details.');
    }
  };

  const fetchModules = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/modules/course/${courseId}`);
      setModules(res.data);
    } catch (err) {
      console.error('Failed to fetch modules');
    }
  };

  const fetchStudents = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/enrollments/course/${courseId}`);
      setStudents(res.data);
    } catch (err) {
      console.error('Failed to fetch enrolled students');
    }
  };

  useEffect(() => {
    setLoading(true);
    Promise.all([fetchCourse(), fetchModules(), fetchStudents()]).finally(() => setLoading(false));
  }, [courseId]);

  const handleEditClick = () => {
    setTitle(course.title);
    setDescription(course.description);
    setThumbnail(course.thumbnailUrl || '');
    setEditMode(true);
  };

  const handleUpdateCourse = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/api/courses/${courseId}`, {
        title,
        description,
        thumbnailUrl: thumbnail,
      });
      await fetchCourse();
      setEditMode(false);
    } catch (err) {
      alert('Failed to update course');
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p className="error-text">{error}</p>;

  return (
    <div className="manage-course-page">
      <div className="course-header">
        {course.thumbnailUrl && (
  <img
    src={course.thumbnailUrl}
    alt="Course Thumbnail"
    className="course-image"
    onError={(e) => (e.target.src = "https://via.placeholder.com/300x200?text=No+Image")}
  />
)}

        <div className="course-details">
          <h1>{course.title}</h1>
          <p>{course.description}</p>
          <p><strong>Modules:</strong> {modules.length}</p>
        </div>
      </div>

      {!editMode ? (
        <button className="btn-edit" onClick={handleEditClick}>Edit Course Info</button>
      ) : (
        <form className="edit-course-form" onSubmit={handleUpdateCourse}>
          <input
            type="text"
            placeholder="Course Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <textarea
            placeholder="Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          ></textarea>
          <input
            type="text"
            placeholder="Thumbnail URL"
            value={thumbnail}
            onChange={(e) => setThumbnail(e.target.value)}
          />
          <button className="btn-save" type="submit">Save</button>
          <button className="btn-cancel" type="button" onClick={() => setEditMode(false)}>Cancel</button>
        </form>
      )}

      <div className="module-section">
        <button className="btn-add-module" onClick={() => setShowModuleForm(!showModuleForm)}>
          {showModuleForm ? 'Close Add Module Form' : 'Add Module'}
        </button>
        {showModuleForm && <AddModuleForm courseId={courseId} onModuleAdded={fetchModules} />}
        <ModuleList modules={modules} onDelete={fetchModules} />
      </div>

      <div className="enrolled-students">
        <h3>Enrolled Students</h3>
        {students.length === 0 ? (
          <p>No students enrolled.</p>
        ) : (
          <ul>
            {students.map((student) => (
              <li key={student.id}>
                {student.firstName} {student.lastName} ({student.email})
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
