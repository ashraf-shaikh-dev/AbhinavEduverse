import React from 'react';
import ReactDOM from 'react-dom/client'; // Import for React 18 root API
import App from './App'; // Main App component with all routes
import { BrowserRouter } from 'react-router-dom'; // Router for SPA navigation
import { AuthProvider } from './context/AuthContext'; // Context provider for auth state

// Create root and render the React app inside it
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode> {/* StrictMode helps catch potential problems in dev */}
    <AuthProvider> {/* Wraps the app so auth state is available globally */}
      <BrowserRouter> {/* Enables routing/navigation in the app */}
        <App /> {/* The main app component */}
      </BrowserRouter>
    </AuthProvider>
  </React.StrictMode>
);
