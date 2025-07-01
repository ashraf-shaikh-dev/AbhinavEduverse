import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

export default function EditCoursePage() {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [thumbnail, setThumbnail] = useState("");

  useEffect(() => {
    axios.get(`http://localhost:8080/api/courses/${courseId}`)
      .then(res => {
        const course = res.data;
        setTitle(course.title);
        setDescription(course.description);
        setThumbnail(course.thumbnailUrl || "");
      });
  }, [courseId]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/api/courses/${courseId}`, {
        title, description, thumbnailUrl: thumbnail
      });
      navigate(".."); // Go back
    } catch {
      alert("Update failed");
    }
  };

  return (
    <form className="edit-course-form" onSubmit={handleUpdate}>
      <input type="text" value={title} onChange={e => setTitle(e.target.value)} />
      <textarea value={description} onChange={e => setDescription(e.target.value)} />
      <input type="text" value={thumbnail} onChange={e => setThumbnail(e.target.value)} />
      <button className="btn-save" type="submit">Save</button>
      <button className="btn-cancel" type="button" onClick={() => navigate(-1)}>Cancel</button>
    </form>
  );
}
