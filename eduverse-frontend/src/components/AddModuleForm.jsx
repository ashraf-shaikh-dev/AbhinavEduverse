import React, { useState } from 'react';
import axios from 'axios';
import '../styles/ModuleForm.css';

export default function AddModuleForm({ courseId, onModuleAdded }) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [loading, setLoading] = useState(false);

  const handleAddModule = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await axios.post(`http://localhost:8080/api/modules/add`, {
        courseId,
        title,
        description
      });
      onModuleAdded();
      setTitle('');
      setDescription('');
    } catch (err) {
      alert('Failed to add module');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="module-form" onSubmit={handleAddModule}>
      <h3>Add New Module</h3>
      <input
        type="text"
        placeholder="Module Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <textarea
        placeholder="Module Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      ></textarea>
      <button type="submit" disabled={loading}>
        {loading ? 'Adding...' : 'Add Module'}
      </button>
    </form>
  );
}
