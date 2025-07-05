import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "../styles/CourseModulePage.css"; // ⬅ Import CSS

export default function CourseModulesPage() {
  const { courseId } = useParams();
  const user = JSON.parse(localStorage.getItem("user"));
  const [modules, setModules] = useState([]);
  const [progress, setProgress] = useState({});
  const [selectedModule, setSelectedModule] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchModulesAndProgress = async () => {
      try {
        const [modulesRes, progressRes] = await Promise.all([
          axios.get(`http://localhost:8080/api/modules/course/${courseId}`),
          axios.get(`http://localhost:8080/api/progress/student/${user.id}/course/${courseId}`),
        ]);

        setModules(modulesRes.data);
        if (modulesRes.data.length > 0) {
          setSelectedModule(modulesRes.data[0]);
        }

        const progressMap = {};
        progressRes.data.forEach((mp) => {
          progressMap[mp.module.id] = mp.completed;
        });
        setProgress(progressMap);
      } catch (err) {
        console.error("Failed to load modules or progress", err);
      } finally {
        setLoading(false);
      }
    };

    fetchModulesAndProgress();
  }, [courseId, user.id]);

  const markComplete = async (moduleId) => {
    try {
      await axios.post(`http://localhost:8080/api/progress/complete`, {
        studentId: user.id,
        moduleId,
        completed: true,
      });
      setProgress((prev) => ({ ...prev, [moduleId]: true }));
    } catch (err) {
      alert("Failed to mark module complete");
    }
  };

  if (loading) return <p>Loading modules...</p>;

  return (
    <div className="course-container">
      {/* Left: Video + Content */}
      <div className="course-left">
        {selectedModule ? (
          <>
            <h2>{selectedModule.title}</h2>
            {selectedModule.videoUrl && (
              <iframe
                src={`https://www.youtube.com/embed/${extractYoutubeId(selectedModule.videoUrl)}`}
                title={selectedModule.title}
                allowFullScreen
              />
            )}
            <p>{selectedModule.content}</p>

            <button
              className={`course-button ${progress[selectedModule.id] ? "completed" : ""}`}
              disabled={progress[selectedModule.id]}
              onClick={() => markComplete(selectedModule.id)}
            >
              {progress[selectedModule.id] ? "Completed" : "Mark as Complete"}
            </button>
          </>
        ) : (
          <p>No module selected</p>
        )}
      </div>

      {/* Right: Module Navigation */}
      <div className="course-right">
        <h3>Modules</h3>
        {modules.map((mod, index) => (
          <button
            key={mod.id}
            onClick={() => setSelectedModule(mod)}
            className={`module-button ${
              selectedModule?.id === mod.id ? "active" : ""
            } ${progress[mod.id] ? "completed" : ""}`}
          >
            Module {index + 1}: {mod.title}
          </button>
        ))}
      </div>
    </div>
  );
}

function extractYoutubeId(url) {
  const regExp =
    /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|watch\?v=)([^#&?]*).*/;
  const match = url.match(regExp);
  return match && match[2].length === 11 ? match[2] : null;
}
