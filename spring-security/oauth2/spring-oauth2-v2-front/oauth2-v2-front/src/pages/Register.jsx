import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../css/Register.css'; 
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import IconButton from '../components/IconButton';
import Container from '../components/Container';
import { isStrongPassword, isValidEmail, isValidURL } from '../helpers/validation';
import { registerUser } from "../services/UserService.js";

function Register() {

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
    setErrors({});
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    let errs = {};

    if (!formData.username) errs.username = 'El nombre de usuario es obligatorio.';
    if (!formData.email) errs.email = 'El email es obligatorio';
    if(formData.email && !isValidEmail(formData.email)) errs.email = 'Email inválido.';
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
    const resul = await registerUser(registerData);
    if(resul != "error") {
      await login();
      navigate('/dashboard');
    }
  }

  const goToLogin = () => {
    navigate('/login');
  }

  return (
    <Container className='container'>
      <IconButton handlerClick={goToLogin} faIcon={faArrowLeft} classNameStyle='go-to-back' />
      <h2>Registro</h2>
      <form onSubmit={handleSubmit} noValidate>
        <div className="form-group">
          <label>Nombre de usuario *</label><br />
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
          {errors.username && <p className="error">{errors.username}</p>}
        </div>

        <div className="form-group">
          <label>Email *</label><br />
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="ejemplo@correo.com"
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
            placeholder="www.mi-web.com"
          />
          {errors.web && <p className="error">{errors.web}</p>}
        </div>

        <div className="form-group">
          <label>Contraseña *</label><br />
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          {errors.password && <p className="error">{errors.password}</p>}
        </div>

        <div className="form-group">
          <label>Confirmar contraseña *</label><br />
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
          />
          {errors.confirmPassword && <p className="error">{errors.confirmPassword}</p>}
        </div>

        <p style={{fontSize: '0.8rem', marginBottom: '0.3rem'}}>(*) Campos obligatorios</p>

        <button className='register-button' type="submit">Registrar</button>
      </form>

      <button onClick={clearFromData} className='clear-button'>Limpiar</button>
    </Container>
  );
}

export default Register;
