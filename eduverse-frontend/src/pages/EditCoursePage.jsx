import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/EditCoursePage.css";

export default function EditCoursePage() {
  const { courseId } = useParams(); // Get courseId from URL
  const navigate = useNavigate();   // Hook to navigate programmatically

  // State for course fields
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [thumbnail, setThumbnail] = useState("");

  // Fetch course data on component mount or when courseId changes
  useEffect(() => {
    axios.get(`http://localhost:8080/api/courses/${courseId}`).then((res) => {
      const course = res.data;
      setTitle(course.title);            // Set title field
      setDescription(course.description);// Set description field
      setThumbnail(course.thumbnailUrl || ""); // Set thumbnail, empty if none
    });
  }, [courseId]);

  // Handle form submission to update course info
  const handleUpdate = async (e) => {
    e.preventDefault(); // Prevent default form submit behavior
    try {
      await axios.put(`http://localhost:8080/api/courses/${courseId}`, {
        title,
        description,
        thumbnailUrl: thumbnail,  // Send thumbnail url as thumbnailUrl
      });
      navigate(".."); // Go back one step in history after successful update
    } catch {
      alert("Update failed");  // Show alert if update request fails
    }
  };

  return (
    <form className="edit-course-form" onSubmit={handleUpdate}>
      <div>
        <label>Title</label>
        {/* Controlled input for course title */}
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </div>

      <div>
        <label>Description</label>
        {/* Controlled textarea for course description */}
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows="5"
        />
      </div>

      <div>
        <label>Thumbnail URL</label>
        {/* Controlled input for thumbnail URL */}
        <input
          type="text"
          value={thumbnail}
          onChange={(e) => setThumbnail(e.target.value)}
        />
        {/* Preview image shown only if thumbnail URL exists */}
        {thumbnail && (
          <img src={thumbnail} alt="Preview" className="thumbnail-preview" />
        )}
      </div>

      <div className="button-group">
        {/* Save button submits the form */}
        <button className="btn-save" type="submit">
          Save
        </button>
        {/* Cancel button navigates back without saving */}
        <button
          className="btn-cancel"
          type="button"
          onClick={() => navigate(-1)}
        >
          Cancel
        </button>
      </div>
    </form>
  );
}
