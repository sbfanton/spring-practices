import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export function AuthProvider({ children }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);

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
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Token inválido');
      }

      const data = await response.json();
      setUserData({
        username: data.username,
        avatarUrl: data.avatarUrl,
        web: data.web,
        email: data.email
      });

      if (data.token) localStorage.setItem("token", data.token);

      setIsAuthenticated(true);
    } catch (error) {
      localStorage.removeItem("token");
      setIsAuthenticated(false);
      setUserData(null);
    } finally {
      setLoading(false);
    }
  };

  const login = async (token) => {
    localStorage.setItem('token', token);
    await checkAuth(); // Actualiza estado global
  };

  const logout = () => {
    localStorage.removeItem('token');
    setIsAuthenticated(false);
    setUserData(null);
  };

  useEffect(() => {
    checkAuth();
  }, []);

  return (
    <AuthContext.Provider value={{
      isAuthenticated,
      userData,
      loading,
      login,
      logout
    }}>
      {children}
    </AuthContext.Provider>
  );
}
