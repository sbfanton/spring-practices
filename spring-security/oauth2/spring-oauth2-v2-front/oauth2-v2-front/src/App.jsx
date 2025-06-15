import React from 'react';
import {BrowserRouter as Router, Routes, Route, Navigate, useNavigate} from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import { AvatarProvider } from "./context/AvatarContext.jsx";
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import EditUser from './pages/EditUser';
import ParticlesBackground from './components/ParticlesBackground';
import './css/App.css';
import './css/AnimatedBackground.css';

function AppRoutes() {
  const { isAuthenticated, loading } = useAuth();
  const navigate = useNavigate();

  if (loading) return <p>Cargando...</p>;

  return (
    <>
      <ParticlesBackground />
      <div className="background">
        <div>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/editUser" element={
                isAuthenticated ? <EditUser /> : <Navigate to="/login" />
            } />
            <Route path="/dashboard" element={
              isAuthenticated ? <Dashboard /> : <Navigate to="/login" />
            } />
            <Route path="/" element={
              isAuthenticated ? <Dashboard /> : <Navigate to="/login" />
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
        <AvatarProvider>
          <AppRoutes />
        </AvatarProvider>
      </AuthProvider>
    </Router>
  );
}