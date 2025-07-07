import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "../styles/CourseModulePage.css"; // Import CSS for styling

export default function CourseModulesPage() {
  const { courseId } = useParams(); // Get courseId from URL
  const user = JSON.parse(localStorage.getItem("user")); // Get logged-in user from localStorage

  const [modules, setModules] = useState([]); // List of modules in the course
  const [progress, setProgress] = useState({}); // Track completion status for modules by moduleId
  const [selectedModule, setSelectedModule] = useState(null); // Module currently being viewed
  const [loading, setLoading] = useState(true); // Loading state

  // Fetch modules and student's progress when courseId or user.id changes
  useEffect(() => {
    const fetchModulesAndProgress = async () => {
      try {
        // Fetch modules and progress in parallel
        const [modulesRes, progressRes] = await Promise.all([
          axios.get(`http://localhost:8080/api/modules/course/${courseId}`),
          axios.get(`http://localhost:8080/api/progress/student/${user.id}/course/${courseId}`),
        ]);

        setModules(modulesRes.data);

        // Automatically select the first module when modules load
        if (modulesRes.data.length > 0) {
          setSelectedModule(modulesRes.data[0]);
        }

        // Convert progress data to a map of moduleId -> completed status
        const progressMap = {};
        progressRes.data.forEach((mp) => {
          progressMap[mp.moduleId] = mp.completed;
        });
        setProgress(progressMap);
      } catch (err) {
        console.error("Failed to load modules or progress", err);
      } finally {
        setLoading(false); // Done loading whether success or error
      }
    };

    fetchModulesAndProgress();
  }, [courseId, user.id]);

  // Mark a module as completed by posting to backend and updating UI
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
      {/* Left side: show selected module's video and content */}
      <div className="course-left">
        {selectedModule ? (
          <>
            <h2>{selectedModule.title}</h2>

            {/* Embed YouTube video if URL exists */}
            {selectedModule.videoUrl && (
              <iframe
                src={`https://www.youtube.com/embed/${extractYoutubeId(selectedModule.videoUrl)}`}
                title={selectedModule.title}
                allowFullScreen
              />
            )}

            <p>{selectedModule.content}</p>

            {/* Button to mark module complete; disabled if already completed */}
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

      {/* Right side: list of modules for navigation */}
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

// Helper function to extract YouTube video ID from URL
function extractYoutubeId(url) {
  const regExp =
    /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|watch\?v=)([^#&?]*).*/;
  const match = url.match(regExp);
  return match && match[2].length === 11 ? match[2] : null;
}
