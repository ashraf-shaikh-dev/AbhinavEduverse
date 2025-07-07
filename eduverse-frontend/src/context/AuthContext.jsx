import React, { createContext, useContext, useState, useEffect } from 'react';

// Create a new context for authentication
const AuthContext = createContext();

// This component provides the authentication context to the rest of the app
export const AuthProvider = ({ children }) => {
  // Check if user is already logged in by reading from localStorage
  const [isLoggedIn, setIsLoggedIn] = useState(() => {
    return localStorage.getItem('isLoggedIn') === 'true';
  });

  // Load user details from localStorage if available
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  });

  // Whenever isLoggedIn or user changes, update localStorage
  useEffect(() => {
    localStorage.setItem('isLoggedIn', isLoggedIn);
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    } else {
      localStorage.removeItem('user');
    }
  }, [isLoggedIn, user]);

  // Function to log in a user and save their data
  const login = (userData) => {
    setUser(userData);
    setIsLoggedIn(true);
  };

  // Function to log out the user and clear their data
  const logout = () => {
    setUser(null);
    setIsLoggedIn(false);
  };

  // The actual value passed to all components that use this context
  return (
    <AuthContext.Provider value={{ isLoggedIn, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// Custom hook to use the AuthContext inside components
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
