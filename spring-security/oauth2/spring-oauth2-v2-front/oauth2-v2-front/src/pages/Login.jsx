import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGithub } from '@fortawesome/free-brands-svg-icons';
import '../css/Login.css';
import showAlert from '../helpers/alertService';
import { useAuth } from '../context/AuthContext';
import Container from "../components/Container";

function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    password: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      const data = await response.json();
      
      if (!response.ok) {
        throw new Error(data.message || "Error desconocido");
      }

      await login(data.token);
      navigate('/dashboard');

    } catch (error) {
      setFormData({
        username: "",
        password: ""
      });
      showAlert({
        title: 'Error de inicio de sesión',
        text: error.message,
        icon: 'error',
        confirmButtonColor: '#3085d6',
        confirmButtonText: "Aceptar"
      });
    }
  };

  const redirectToRegister = () => {
    navigate('/register');
  };

  const handleGitHubLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/login?provider=github';
  };

  return (
    <Container className='container'>
      <h2>Iniciar sesión</h2>

      <form onSubmit={handleLogin} className="form">
        <input
          type="text"
          name="username"
          placeholder="Usuario"
          required
          className="input"
          value={formData.username}
          onChange={handleChange}
        />
        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          required
          className="input"
          value={formData.password}
          onChange={handleChange}
        />
        <button type="submit" className="signin-button">Entrar</button>
      </form>

      <button onClick={handleGitHubLogin} className="social-button">
        <FontAwesomeIcon icon={faGithub} style={{ marginRight: '8px' }} />
        Iniciar sesión con GitHub
      </button>

      <hr className="separator" />

      <button onClick={redirectToRegister} className="link-button">
        ¿No tenés cuenta? Registrate
      </button>
    </Container>
  );
}

export default Login;
