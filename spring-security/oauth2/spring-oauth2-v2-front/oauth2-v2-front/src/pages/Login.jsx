import React from 'react';
import { useNavigate } from 'react-router-dom'; // si usás React Router
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGithub } from '@fortawesome/free-brands-svg-icons';
import '../css/Login.css';

function Login() {
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    // Lógica de login...
    console.log('Iniciando sesión...');
  };

  const redirectToRegister = () => {
    //navigate('/register');
    // Lógica de login...
    console.log('Registrando usuario...');
  };

  const handleGitHubLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/login?provider=github';
  };

  return (
    <div className="container">
      <h2>Iniciar sesión</h2>

      <form onSubmit={handleLogin} className="form">
        <input
          type="text"
          name="username"
          placeholder="Usuario"
          required
          className="input"
        />
        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          required
          className="input"
        />
        <button type="submit" className="signin-button">Entrar</button>
      </form>

      <button onClick={redirectToRegister} className="link-button">
        ¿No tenés cuenta? Registrate
      </button>

      <hr className="separator" />

      <button onClick={handleGitHubLogin} className="github-button">
        <FontAwesomeIcon icon={faGithub} style={{ marginRight: '8px' }} />
        Iniciar sesión con GitHub
      </button>
    </div>
  );
}

export default Login;
