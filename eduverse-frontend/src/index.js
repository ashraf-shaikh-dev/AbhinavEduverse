import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './styles/global.css'; // Global CSS for your app

// Create React root (React 18+)
const root = ReactDOM.createRoot(document.getElementById('root'));

// Render the main App
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
