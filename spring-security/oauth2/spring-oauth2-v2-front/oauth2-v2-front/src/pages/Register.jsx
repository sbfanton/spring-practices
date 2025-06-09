import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../css/Register.css'; 
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import IconButton from '../components/IconButton';

function Registro() {

  const { login } = useAuth();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: '',
    email: '',
    web: '',
    password: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({});

  const isStrongPassword = (password) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
    return regex.test(password);
  };

  const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

  const isValidURL = (url) => {
    try {
      new URL(url);
      return true;
    } catch {
      return false;
    }
  };

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const clearFromData = () => {
    let form = {
      username: '',
      email: '',
      web: '',
      password: '',
      confirmPassword: ''
    }

    setFormData(form);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    let errs = {};

    if (!formData.username) errs.username = 'El nombre de usuario es obligatorio.';
    if (!formData.email || !isValidEmail(formData.email)) errs.email = 'Email inválido.';
    if (formData.web && !isValidURL(formData.web)) errs.web = 'URL inválida.';
    if (!isStrongPassword(formData.password)) errs.password = 'Contraseña débil. Debe tener mínimo 8 caracteres, mayúscula, minúscula, número y carácter especial.';
    if (formData.password !== formData.confirmPassword) errs.confirmPassword = 'Las contraseñas no coinciden.';

    setErrors(errs);

    if (Object.keys(errs).length === 0) {
      await register();
    }
  };

  const register = async () => {
    let registerData = {
        username: formData.username,
        password: formData.password,
        email: formData.email,
        web: formData.web,
        avatarUrl: ""
    }

    try {
      const response = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(registerData)
      });

      const data = await response.json();
      
      if (!response.ok) {
        throw new Error(data.message || "Error desconocido");
      }

      await login(data.token);
      navigate('/dashboard');

    } catch (error) {
      showAlert({
        title: 'Error de registro',
        text: error.message,
        icon: 'error',
        confirmButtonColor: '#3085d6',
        confirmButtonText: "Aceptar"
      });
    }
  }

  const goToLogin = () => {
    navigate('/login');
  }

  return (
    <div className="registro-container">
      <IconButton handlerClick={goToLogin} faIcon={faArrowLeft} classNameStyle='go-to-login' />
      <h2>Registro</h2>
      <form onSubmit={handleSubmit} noValidate>
        <div className="form-group">
          <label>Nombre de usuario</label><br />
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
          {errors.username && <p className="error">{errors.username}</p>}
        </div>

        <div className="form-group">
          <label>Email</label><br />
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && <p className="error">{errors.email}</p>}
        </div>

        <div className="form-group">
          <label>Web (URL)</label><br />
          <input
            type="url"
            name="web"
            value={formData.web}
            onChange={handleChange}
          />
          {errors.web && <p className="error">{errors.web}</p>}
        </div>

        <div className="form-group">
          <label>Contraseña</label><br />
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          {errors.password && <p className="error">{errors.password}</p>}
        </div>

        <div className="form-group">
          <label>Confirmar contraseña</label><br />
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
          />
          {errors.confirmPassword && <p className="error">{errors.confirmPassword}</p>}
        </div>

        <button className='register-button' type="submit">Registrar</button>
      </form>

      <button onClick={clearFromData} className='clear-button'>Limpiar</button>
    </div>
  );
}

export default Registro;
