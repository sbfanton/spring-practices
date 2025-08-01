import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/EditUser.css'; 
import { isStrongPassword, isValidEmail, isValidURL } from '../helpers/validation';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import Container from '../components/Container';
import IconButton from '../components/IconButton';
import EditableAvatar from "../components/EditableAvatar.jsx";
import { changePassword, getUserInfo, changeUserInfo, resendVerificationEmail } from "../services/UserService.js";
import alertService from "../helpers/alertService.js";
import { useAuth } from "../context/AuthContext.jsx";

export default function EditUser() {

  const navigate = useNavigate();
  const { userData, setUserData } = useAuth();

 const [generalData, setGeneralData] = useState({
     username: userData.username,
     email: userData.email,
     website: userData.web,
     hasPassword: userData.hasPassword
  });

  const [passwordData, setPasswordData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
    isFirstPassword: !userData.hasPassword
  });

  const [resent, setResent] = useState(false);
  const [sending, setSending] = useState(false);

  const [errors, setErrors] = useState({});

  const handleGeneralChange = (e) => {
    const { name, value } = e.target;
    setGeneralData(prev => ({ ...prev, [name]: value }));
  };

  const handlePasswordChange = (e) => {
    const { name, value } = e.target;
    setPasswordData(prev => ({ ...prev, [name]: value }));
  };

  const handleGeneralSubmit = async (e) => {
    e.preventDefault();

    let errs = {};

    if (!generalData.username || !isValidUsername(generalData.username)) errs.username = "Usuario inválido";
    if (!generalData.email || !isValidEmail(generalData.email)) errs.email = 'Email inválido.';
    if (generalData.web && !isValidURL(generalData.web)) errs.web = 'URL inválida.';

    setErrors(errs);

    if (Object.keys(errs).length === 0) {
      await changeUserInfo(generalData);
      setUserData(prev => ({
        ...prev,
          username: generalData.username,
        email: generalData.email,
        web: generalData.website
      }));
    }
  };

    const isValidUsername = (text) => {
        const regex = /^[A-Za-z0-9_]+$/;
        return regex.test(text);
    }

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();

    let errs = {};

    if (userData.hasPassword && !passwordData.currentPassword) errs.currentPassword = 'Debe introducir la contraseña actual';
    if (!isStrongPassword(passwordData.newPassword)) errs.newPassword = 'Contraseña débil. Debe tener mínimo 8 caracteres, mayúscula, minúscula, número y carácter especial.';
    if (passwordData.newPassword !== passwordData.confirmPassword) errs.confirmPassword = 'Las contraseñas no coinciden.';

    setErrors(errs);

    if (Object.keys(errs).length === 0) {
        await changePassword(passwordData);
        setUserData(prev => ({
            ...prev,
            hasPassword: true
          }));
          setPasswordData({
            currentPassword: '',
            newPassword: '',
            confirmPassword: '',
            isFirstPassword: !userData.hasPassword
          })
    }
  };

  const goToDashboard = () => {
    navigate('/dashboard');
  }

  const avatarStyle = {
    width: '10rem', 
    height: '10rem', 
    borderRadius: '50%', 
    objectFit: 'cover'
  }

  const avatarUrl = "";

  const resendVerificationMail = async () => {
    await resendVerificationEmail(userData.email);
  }

  useEffect( () => {
    async function fetchData() {
      const data = await getUserInfo();
    }
    fetchData();
  }, []);

  return (
    <Container className='container'>
        <IconButton handlerClick={goToDashboard} faIcon={faArrowLeft} classNameStyle='go-to-back' />
        <div className="edit-user-container">
        <h2>Editar Perfil</h2>

          {/* Avatar editable */}
          <div className='profile-avatar-container'>
            <EditableAvatar
                style={{width: '9rem', height: '9rem'}}
            />
          </div>

        {/* Formulario de datos generales */}
        <form onSubmit={handleGeneralSubmit} noValidate>
            <div className="form-group">
                <label>Nombre de usuario</label>
                <input
                    type="text"
                    name="username"
                    value={generalData.username}
                    onChange={handleGeneralChange}
                    placeholder="Tu nombre de usuario"
                />
                {errors.username && <p className="error">{errors.username}</p>}
            </div>

          <div className="form-group">
            <label>Sitio web</label>
            <input
                type="url"
                name="website"
                value={generalData.website}
                onChange={handleGeneralChange}
                placeholder="www.mi-web.com"
            />
          </div>

            <div className="form-group">
            <label>Email</label>
            <input
                type="email"
                name="email"
                value={generalData.email}
                onChange={handleGeneralChange}
                placeholder="ejemplo@correo.com"
            />
            {errors.email && <p className="error">{errors.email}</p>}
            </div>

          {userData && userData.isEmailVerified === false && (
              <div className="email-verification">
                <button
                    type="button"
                    onClick={resendVerificationMail}
                    className="resend-button"
                >
                  Verificar email
                </button>
              </div>
          )}

            <button type="submit" className='save-edit-button'>Guardar datos</button>
        </form>

        <hr className="divider" />

        {/* Formulario de cambio de contraseña */}
        {userData === null ? null : (
          userData.hasPassword ? <h3>Cambiar contraseña</h3> : <h3>Setear contraseña</h3>
        )}

        <form onSubmit={handlePasswordSubmit} noValidate>
            {userData.hasPassword && (<div className="form-group" >
            <label>Contraseña actual</label>
            <input
                type="password"
                name="currentPassword"
                value={passwordData.currentPassword}
                onChange={handlePasswordChange}
            />
            {errors.currentPassword && <p className="error">{errors.currentPassword}</p>}
            </div>)}

            <div className="form-group">
            <label>Nueva contraseña</label>
            <input
                type="password"
                name="newPassword"
                value={passwordData.newPassword}
                onChange={handlePasswordChange}
            />
            {errors.newPassword && <p className="error">{errors.newPassword}</p>}
            </div>

            <div className="form-group">
            <label>Confirmar nueva contraseña</label>
            <input
                type="password"
                name="confirmPassword"
                value={passwordData.confirmPassword}
                onChange={handlePasswordChange}
            />
            {errors.confirmPassword && <p className="error">{errors.confirmPassword}</p>}
            </div>

            <button type="submit" className='save-edit-button'>Actualizar contraseña</button>
        </form>
        </div>
    </Container>
  );
}

