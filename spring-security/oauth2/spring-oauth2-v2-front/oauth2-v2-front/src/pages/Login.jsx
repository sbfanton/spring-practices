import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGithub, faGoogle } from '@fortawesome/free-brands-svg-icons';
import '../css/Login.css';
import { useAuth } from '../context/AuthContext';
import Container from "../components/Container";
import { handleSocialLogin, loginUser } from "../services/UserService.js";

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
    await loginUser(formData.username, formData.password);
    await login();
    navigate('/dashboard');
  };

  const redirectToRegister = () => {
    navigate('/register');
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

      <button onClick={() => handleSocialLogin("github")} className="social-button">
        <FontAwesomeIcon icon={faGithub} style={{ marginRight: '8px' }} />
        Iniciar sesión con GitHub
      </button>

        <button onClick={() => handleSocialLogin("google")} className="social-button">
            <FontAwesomeIcon icon={faGoogle} style={{ marginRight: '8px' }} />
            Iniciar sesión con Google
        </button>

      <hr className="separator" />

      <button onClick={redirectToRegister} className="link-button">
        ¿No tenés cuenta? Registrate
      </button>
    </Container>
  );
}

export default Login;
