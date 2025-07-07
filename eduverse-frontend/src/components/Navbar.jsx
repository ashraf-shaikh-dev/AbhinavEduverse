import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";
import "../styles/Navbar.css";

export default function Navbar() {
  const { isLoggedIn, user, logout } = useAuth();
  const navigate = useNavigate();
  const [menuActive, setMenuActive] = useState(false);

  const handleLogout = () => {
    if (window.confirm("Are you sure you want to logout?")) {
      logout();
      navigate("/");
    }
  };

  const toggleMenu = () => {
    setMenuActive((prev) => !prev);
  };

  const closeMenu = () => setMenuActive(false);

  const renderLinks = () => {
    const commonLinks = (
      <>
        <li>
          <Link to="/" onClick={closeMenu}>
            Home
          </Link>
        </li>
        <li>
          <Link to="/courses" onClick={closeMenu}>
            Courses
          </Link>
        </li>
        <li>
          <Link to="/about" onClick={closeMenu}>
            About
          </Link>
        </li>
      </>
    );

    if (!isLoggedIn) {
      return (
        <>
          {commonLinks}
          <li>
            <Link to="/login" onClick={closeMenu}>
              Login
            </Link>
          </li>
          <li>
            <Link to="/signup" onClick={closeMenu}>
              Signup
            </Link>
          </li>
        </>
      );
    }

    return (
      <>
        {commonLinks}
        <li>
          <Link
            to={
              user?.role === "STUDENT"
                ? "/student/dashboard"
                : "/teacher/dashboard"
            }
            onClick={closeMenu}
          >
            Hi,{" "}
            {user?.firstName ||
              (user?.role === "STUDENT" ? "Student" : "Teacher")}
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
      <div className="navbar-brand">
        <Link to="/" onClick={closeMenu}>
          Abhinav-Eduverse
        </Link>
      </div>

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

      <ul className={`navbar-links ${menuActive ? "active" : ""}`}>
        {renderLinks()}
      </ul>
    </nav>
  );
}
