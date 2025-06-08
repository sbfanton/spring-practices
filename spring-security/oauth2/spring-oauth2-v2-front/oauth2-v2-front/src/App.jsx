import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Login from './pages/Login';
import Dashboard from './pages/Dashboard';

function AppRouter() {
  const [loading, setLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userData, setUserData] = useState({});

  useEffect(() => {
    const checkAuth = async () => {
      const tokenLocal = localStorage.getItem('token');
      const params = new URLSearchParams(window.location.search);
      const paramToken = params.get("token");

      if (!tokenLocal && !paramToken) {
        setIsAuthenticated(false);
        setLoading(false);
        return;
      }

      try {
        let token = tokenLocal || paramToken;
        const response = await fetch('http://localhost:8080/user-info', {
          method: 'GET',
          headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          localStorage.removeItem("token");
          throw new Error('Token inválido');
        }

        const data = await response.json();
        if(data.token != null) {
          let user = {
            username: data.username,
            avatarUrl: data.avatarUrl,
            web: data.web
          };
          localStorage.setItem("token", data.token)
          setUserData(user);
          setIsAuthenticated(true);
        }
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
  }, []);

  if (loading) return <div>Cargando...</div>;

  return (
    <div style={styles.centeredContainer}>
      <Routes>
        <Route path="/login" element={isAuthenticated ? <Navigate to="/dashboard" /> : <Login />} />
        <Route path="/dashboard" element={isAuthenticated ? <Dashboard user={userData}/> : <Navigate to="/login" />} />
        <Route path="/" element={<Navigate to={isAuthenticated ? "/dashboard" : "/login"} />} />
      </Routes>
    </div>
  );
}

export default function App() {
  return (
    <Router>
      <AppRouter />
    </Router>
  );
}

const styles = {
  centeredContainer: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
    flexDirection: "column",
    width: "100vw",
    height: "100vh"
  },
};