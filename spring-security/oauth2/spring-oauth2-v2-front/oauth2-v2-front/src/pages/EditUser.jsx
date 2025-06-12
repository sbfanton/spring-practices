import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/EditUser.css'; 
import { isStrongPassword, isValidEmail, isValidURL } from '../helpers/validation';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import Container from '../components/Container';
import IconButton from '../components/IconButton';
import UserAvatar from '../components/UserAvatar';
import defaultAvatar from '../assets/default-avatar.jpg';

export default function EditUser() {

  const navigate = useNavigate();

  const [avatarFile, setAvatarFile] = useState(null);
  const [avatarUrl, setAvatarUrl] = useState(null);

  const [generalData, setGeneralData] = useState({
    email: '',
    website: '',
  });

  const [passwordData, setPasswordData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({});

  const handleGeneralChange = (e) => {
    const { name, value } = e.target;
    setGeneralData(prev => ({ ...prev, [name]: value }));
  };

  const handleAvatarChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setAvatarFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setAvatarPreview(reader.result); // Base64 para mostrar
      };
      reader.readAsDataURL(file);
    }
  };

  const handlePasswordChange = (e) => {
    const { name, value } = e.target;
    setPasswordData(prev => ({ ...prev, [name]: value }));
  };

  const handleGeneralSubmit = (e) => {
    e.preventDefault();

    let errs = {};

    if (generalData.email && !isValidEmail(generalData.email)) errs.email = 'Email inválido.';
    if (generalData.web && !isValidURL(generalData.web)) errs.web = 'URL inválida.';

    setErrors(errs);

    if (Object.keys(errs).length === 0) {
        const formData = new FormData();
        formData.append('email', generalData.email);
        formData.append('website', generalData.website);
        if (avatarFile) {
            formData.append('avatar', avatarFile);
        }
        console.log('Datos a enviar:', formData);
    }
  };

  const handlePasswordSubmit = (e) => {
    e.preventDefault();

    let errs = {};

    if (!passwordData.currentPassword) errs.currentPassword = 'Debe introducir la contraseña actual';
    if (!isStrongPassword(passwordData.newPassword)) errs.newPassword = 'Contraseña débil. Debe tener mínimo 8 caracteres, mayúscula, minúscula, número y carácter especial.';
    if (passwordData.newPassword !== passwordData.confirmPassword) errs.confirmPassword = 'Las contraseñas no coinciden.';

    if (Object.keys(errs).length === 0) {
        console.log('Contraseña cambiada:', passwordData);
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

  return (
    <Container className='container'>
        <IconButton handlerClick={goToDashboard} faIcon={faArrowLeft} classNameStyle='go-to-back' />
        <div className="edit-user-container">
        <h2>Editar Perfil</h2>

        {/* Formulario de datos generales */}
        <form onSubmit={handleGeneralSubmit} noValidate>
            
            <div className='profile-avatar-container'>
                <UserAvatar
                    url={avatarUrl ? avatarUrl : defaultAvatar}
                    alt="Avatar de usuario"
                    styles={avatarStyle}
                />
            </div>

            <div className="form-group">
                <label>Avatar</label><br />
                <input type="file" accept="image/*" onChange={handleAvatarChange} />
            </div>

            <div className="form-group">
            <label>Email</label>
            <input
                type="email"
                name="email"
                value={generalData.email}
                onChange={handleGeneralChange}
            />
            {errors.email && <p className="error">{errors.email}</p>}
            </div>

            <div className="form-group">
            <label>Sitio web</label>
            <input
                type="url"
                name="website"
                value={generalData.website}
                onChange={handleGeneralChange}
            />
            </div>

            <button type="submit" className='save-edit-button'>Guardar datos</button>
        </form>

        <hr className="divider" />

        {/* Formulario de cambio de contraseña */}
        <h3>Cambiar contraseña</h3>
        <form onSubmit={handlePasswordSubmit} noValidate>
            <div className="form-group">
            <label>Contraseña actual</label>
            <input
                type="password"
                name="currentPassword"
                value={passwordData.currentPassword}
                onChange={handlePasswordChange}
            />
            {errors.currentPassword && <p className="error">{errors.currentPassword}</p>}
            </div>

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

