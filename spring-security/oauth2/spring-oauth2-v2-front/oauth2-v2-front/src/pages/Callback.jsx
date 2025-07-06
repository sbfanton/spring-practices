import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from "../context/AuthContext.jsx";
import Cookies from 'js-cookie';
import showAlert from '../helpers/alertService';

const Callback = () => {
  const navigate = useNavigate();
  const { login } = useAuth();

  useEffect(() => {
    const handleCallback = async () => {
      try {
        const error = Cookies.get("oauth2_error");
        if (error) {
          const errorMsg = decodeURIComponent(error);
          showAlert({
            title: 'Error',
            text: errorMsg,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar",
            onConfirm: () => {
              Cookies.remove("oauth2_error");
              navigate("/login");
            }
          });
        }
        else {
          await login();
          navigate('/dashboard');
        }
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