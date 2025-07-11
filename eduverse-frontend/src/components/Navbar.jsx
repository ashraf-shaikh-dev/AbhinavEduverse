import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";
import "../styles/Navbar.css";

// This component displays the top navigation bar
export default function Navbar() {
  const { isLoggedIn, user, logout } = useAuth(); // Get auth info from context
  const navigate = useNavigate();
  const [menuActive, setMenuActive] = useState(false); // For responsive menu (mobile)

  // Logout function
  const handleLogout = () => {
    if (window.confirm("Are you sure you want to logout?")) {
      logout();      // clear login session
      navigate("/"); // go to home page
    }
  };

  // Toggle the menu open/close for mobile view
  const toggleMenu = () => {
    setMenuActive((prev) => !prev);
  };

  // Close the menu when a link is clicked
  const closeMenu = () => setMenuActive(false);

  // This function returns different menu items based on login status
  const renderLinks = () => {
    // Links shown to everyone
    const commonLinks = (
      <>
        <li>
          <Link to="/" onClick={closeMenu}>Home</Link>
        </li>
        <li>
          <Link to="/courses" onClick={closeMenu}>Courses</Link>
        </li>
        <li>
          <Link to="/about" onClick={closeMenu}>About</Link>
        </li>
      </>
    );

    // If user is not logged in, show login/signup
    if (!isLoggedIn) {
      return (
        <>
          {commonLinks}
          <li>
            <Link to="/login" onClick={closeMenu}>Login</Link>
          </li>
          <li>
            <Link to="/signup" onClick={closeMenu}>Signup</Link>
          </li>
        </>
      );
    }

    // If user is logged in, show dashboard and logout button
    return (
      <>
        {commonLinks}
        <li>
          <Link
            to={user?.role === "STUDENT" ? "/student/dashboard" : "/teacher/dashboard"}
            onClick={closeMenu}
          >
            Hi, {user?.firstName || (user?.role === "STUDENT" ? "Student" : "Teacher")}
          </Link>
        </li>
        <li>
          <button
            className="logout-btn"
            onClick={() => {
              handleLogout();
              closeMenu();
            }}
          >
            Logout
          </button>
        </li>
      </>
    );
  };

  return (
    <nav className="navbar">
      {/* Logo or brand */}
      <div className="navbar-brand">
        <Link to="/" onClick={closeMenu}><img src="/logo.png" alt=""/></Link>
        <Link to="/" onClick={closeMenu}>Abhinav-Eduverse</Link>
      </div>

      {/* Menu button for small screens */}
      <div
        className="menu-toggle"
        onClick={toggleMenu}
        aria-label="Toggle menu"
        role="button"
        tabIndex={0}
        aria-expanded={menuActive}
      >
        {menuActive ? "✕" : "☰"}
      </div>

      {/* Navigation links */}
      <ul className={`navbar-links ${menuActive ? "active" : ""}`}>
        {renderLinks()}
      </ul>
    </nav>
  );
}
