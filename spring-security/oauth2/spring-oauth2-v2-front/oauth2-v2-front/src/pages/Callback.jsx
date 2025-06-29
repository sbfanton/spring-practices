import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { postCallbackReq } from '../services/UserService.js';
import { useAuth } from "../context/AuthContext.jsx";

const Callback = () => {
  const navigate = useNavigate();
  const { login } = useAuth();

  useEffect(() => {
    const handleCallback = async () => {
      try {
        const token = await postCallbackReq();
        await login(token);
        navigate('/dashboard');
      } catch (error) {
        console.error('Error en el callback:', error);
        navigate('/login');
      }
    };

    handleCallback();
  }, []);

  return (
      <div style={{ textAlign: 'center', marginTop: '2rem' }}>
        <p>Procesando inicio de sesión...</p>
      </div>
  );
};

export default Callback;