import React, { useState } from "react";
import axios from "axios";
import "../styles/ModuleList.css";

export default function ModuleList({ modules, onDelete }) {
  const [editingModuleId, setEditingModuleId] = useState(null);
  const [expandedModuleId, setExpandedModuleId] = useState(null);
  const [editedModule, setEditedModule] = useState({
    title: "",
    content: "",
    videoUrl: "",
  });

  const handleDelete = async (moduleId) => {
    if (!window.confirm("Delete this module?")) return;
    try {
      await axios.delete(`http://localhost:8080/api/modules/${moduleId}`);
      onDelete();
    } catch {
      alert("Failed to delete module");
    }
  };

  const handleEditClick = (mod) => {
    setEditingModuleId(mod.id);
    setEditedModule({
      title: mod.title,
      content: mod.content,
      videoUrl: mod.videoUrl,
    });
  };

  const handleUpdate = async (moduleId) => {
    try {
      await axios.put(`http://localhost:8080/api/modules/${moduleId}`, editedModule);
      setEditingModuleId(null);
      onDelete();
    } catch {
      alert("Failed to update module");
    }
  };

  const handleChange = (e) => {
    setEditedModule({ ...editedModule, [e.target.name]: e.target.value });
  };

  const toggleExpand = (moduleId) => {
    setExpandedModuleId((prev) => (prev === moduleId ? null : moduleId));
  };

  const getYoutubeEmbedUrl = (videoUrl) => {
    if (!videoUrl) return "";
    const regExp = /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|watch\?v=)([^#&?]*).*/;
    const match = videoUrl.match(regExp);
    return match && match[2].length === 11
      ? `https://www.youtube.com/embed/${match[2]}`
      : "";
  };

  if (modules.length === 0) return <p>No modules yet.</p>;

  return (
    <div className="module-list">
      <h3>Modules</h3>
      <ul>
        {modules.map((mod, index) => (
          <li key={mod.id} className="module-item">
            {editingModuleId === mod.id ? (
              <div className="edit-module-form">
                <input
                  type="text"
                  name="title"
                  value={editedModule.title}
                  onChange={handleChange}
                  placeholder="Module Title"
                />
                <textarea
                  name="content"
                  value={editedModule.content}
                  onChange={handleChange}
                  placeholder="Module Content"
                />
                <input
                  type="text"
                  name="videoUrl"
                  value={editedModule.videoUrl}
                  onChange={handleChange}
                  placeholder="YouTube URL"
                />
                <div className="module-buttons">
                  <button onClick={() => handleUpdate(mod.id)}>Save</button>
                  <button onClick={() => setEditingModuleId(null)}>Cancel</button>
                </div>
              </div>
            ) : (
              <>
                <div className="module-header">
                  <strong>{`Module ${index + 1}: ${mod.title}`}</strong>
                  <button
                    className="expand-btn"
                    onClick={() => toggleExpand(mod.id)}
                    aria-label={expandedModuleId === mod.id ? "Collapse module" : "Expand module"}
                  >
                    {expandedModuleId === mod.id ? "🔽 Collapse " : "▶️ Expand "}
                  </button>
                </div>

                <div className={`module-body ${expandedModuleId === mod.id ? "expanded" : ""}`}>
                  <div className="module-body-inner">
                    {getYoutubeEmbedUrl(mod.videoUrl) ? (
                      <iframe
                        width="100%"
                        height="200"
                        src={getYoutubeEmbedUrl(mod.videoUrl)}
                        title={mod.title}
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowFullScreen
                      />
                    ) : (
                      <div className="no-video">No video preview available</div>
                    )}
                    <p>{mod.content}</p>
                    <div className="module-buttons">
                      <button onClick={() => handleEditClick(mod)}>Edit</button>
                      <button onClick={() => handleDelete(mod.id)}>Delete</button>
                    </div>
                  </div>
                </div>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
