import React from 'react';
import {BrowserRouter as Router, Routes, Route, Navigate, useNavigate} from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import { AvatarProvider } from "./context/AvatarContext.jsx";
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import EditUser from './pages/EditUser';
import Callback from "./pages/Callback";
import ParticlesBackground from './components/ParticlesBackground';
import './css/App.css';
import './css/AnimatedBackground.css';
import { LoadingProvider } from './context/LoadingContext';
import Loader from './components/Loader';

function AppRoutes() {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

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
            <Route path="/auth/callback" element={<Callback />} />
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
          <LoadingProvider>
            <Loader />
            <AppRoutes />
          </LoadingProvider>
        </AvatarProvider>
      </AuthProvider>
    </Router>
  );
}