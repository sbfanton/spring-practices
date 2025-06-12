import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import EditUser from './pages/EditUser';
import ParticlesBackground from './components/ParticlesBackground';
import './css/App.css';
import './css/AnimatedBackground.css';

function AppRoutes() {
  const { isAuthenticated, loading } = useAuth();

  if (loading) return <p>Cargando...</p>;

  return (
    <>
      <ParticlesBackground />
      <div className="background">
        <div>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/editUser" element={<EditUser />} />
            <Route path="/dashboard" element={
              isAuthenticated ? <Dashboard /> : <Navigate to="/login" />
            } />
            <Route path="/" element={
              <Navigate to={isAuthenticated ? "/dashboard" : "/login"} />
            } />
          </Routes>
        </div>
      </div>
    </>
  );
}

export default function App() {
  return (
    <Router>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </Router>
  );
}