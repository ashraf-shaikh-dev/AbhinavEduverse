import React from 'react';
import axios from 'axios';
import '../styles/ModuleForm.css';

export default function ModuleList({ modules, onDelete }) {
  const handleDelete = async (moduleId) => {
    if (!window.confirm('Delete this module?')) return;
    try {
      await axios.delete(`http://localhost:8080/api/modules/${moduleId}`);
      onDelete();
    } catch (err) {
      alert('Failed to delete module');
    }
  };

  if (modules.length === 0) return <p>No modules yet.</p>;

  return (
    <div className="module-list">
      <h3>Modules</h3>
      <ul>
        {modules.map((mod) => (
          <li key={mod.id}>
            <strong>{mod.title}</strong>
            <p>{mod.description}</p>
            <button onClick={() => handleDelete(mod.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
