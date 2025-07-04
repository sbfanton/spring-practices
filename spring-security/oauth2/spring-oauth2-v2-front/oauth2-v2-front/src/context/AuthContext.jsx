import React, { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { getUserInfo, logoutUser } from "../services/UserService.js";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export function AuthProvider({ children }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const checkAuth = async () => {
    try {
      const data = await getUserInfo();
      setUserData({
        username: data.username,
        avatarUrl: data.avatarUrl || "",
        web: data.web || "",
        email: data.email || ""
      });
      setIsAuthenticated(true);
    } catch (error) {
      setIsAuthenticated(false);
      setUserData(null);
    } finally {
      setLoading(false);
    }
  };

  const login = async () => {
    await checkAuth();
  };

  const logout = async () => {
    await logoutUser();
    setIsAuthenticated(false);
    setUserData(null);
  };

  useEffect( () => {
    checkAuth();
  }, []);

  return (
    <AuthContext.Provider value={{
      isAuthenticated,
      userData,
      loading,
      setUserData,
      login,
      logout
    }}>
      {children}
    </AuthContext.Provider>
  );
}
