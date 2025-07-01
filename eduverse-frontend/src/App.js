import React from "react";
import { Routes, Route } from "react-router-dom";
import SignupPage from "./pages/SignupPage";
import LoginPage from "./pages/LoginPage";
import StudentDashboard from "./pages/StudentDashboard";
import TeacherDashboard from "./pages/TeacherDashboard";
import CreateCourse from "./pages/CreateCourse";
import LandingPage from "./pages/LandingPage";
import Courses from "./pages/CoursesPage";
import Navbar from "./components/Navbar";
import About from "./pages/AboutPage";

// Manage Course Feature
import ManageCourseLayout from "./pages/ManageCourseLayout";
import CourseInfoPage from "./pages/CourseInfoPage";
import EditCoursePage from "./pages/EditCoursePage";
import ModuleListPage from "./pages/ModuleListPage";
import AddModulePage from "./pages/AddModulePage";

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/about" element={<About />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />

        <Route path="/student/dashboard" element={<StudentDashboard />} />
        <Route path="/teacher/dashboard" element={<TeacherDashboard />} />
        <Route path="/teacher/create-course" element={<CreateCourse />} />
        <Route path="/courses" element={<Courses />} />

        {/* Manage Course Section */}
        <Route path="/manage-course/:courseId" element={<ManageCourseLayout />}>
          <Route index element={<CourseInfoPage />} />
          <Route path="edit" element={<EditCoursePage />} />
          <Route path="add-module" element={<AddModulePage />} />
          <Route path="modules" element={<ModuleListPage />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
