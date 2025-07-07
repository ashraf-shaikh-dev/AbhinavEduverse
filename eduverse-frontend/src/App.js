import React from "react";
import { Routes, Route } from "react-router-dom";

// Import all the page components
import SignupPage from "./pages/SignupPage";
import LoginPage from "./pages/LoginPage";
import StudentDashboard from "./pages/StudentDashboard";
import TeacherDashboard from "./pages/TeacherDashboard";
import CreateCourse from "./pages/CreateCourse";
import LandingPage from "./pages/LandingPage";
import Courses from "./pages/CoursesPage";
import Navbar from "./components/Navbar";
import About from "./pages/AboutPage";
import CourseDetailsPage from "./pages/CourseDetailsPage";
import CourseModulesPage from "./pages/CourseModulePage";

// Manage Course Feature related pages
import ManageCourseLayout from "./pages/ManageCourseLayout";
import CourseInfoPage from "./pages/CourseInfoPage";
import EditCoursePage from "./pages/EditCoursePage";
import ModuleListPage from "./pages/ModuleListPage";
import AddModulePage from "./pages/AddModulePage";

function App() {
  return (
    <>
      {/* Navbar shown on every page */}
      <Navbar />

      {/* Define all routes here */}
      <Routes>
        {/* Public routes */}
        <Route path="/" element={<LandingPage />} />       {/* Home / Landing */}
        <Route path="/about" element={<About />} />        {/* About page */}
        <Route path="/signup" element={<SignupPage />} />  {/* Signup page */}
        <Route path="/login" element={<LoginPage />} />    {/* Login page */}

        {/* Student dashboard route */}
        <Route path="/student/dashboard" element={<StudentDashboard />} />

        {/* Teacher dashboard routes */}
        <Route path="/teacher/dashboard" element={<TeacherDashboard />} />
        <Route path="/teacher/create-course" element={<CreateCourse />} />

        {/* Course listing and details */}
        <Route path="/courses" element={<Courses />} />                     {/* List all courses */}
        <Route path="/courses/:courseId" element={<CourseDetailsPage />} /> {/* Specific course details */}
        <Route path="/course-modules/:courseId" element={<CourseModulesPage />} /> {/* Course modules */}

        {/* Manage Course nested routes - teacher can manage a specific course */}
        <Route path="/manage-course/:courseId" element={<ManageCourseLayout />}>
          <Route index element={<CourseInfoPage />} />          {/* Default info page */}
          <Route path="edit" element={<EditCoursePage />} />    {/* Edit course details */}
          <Route path="add-module" element={<AddModulePage />} />{/* Add new module */}
          <Route path="modules" element={<ModuleListPage />} />  {/* List modules */}
        </Route>
      </Routes>
    </>
  );
}

export default App;
