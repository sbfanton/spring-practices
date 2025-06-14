import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/EditUser.css'; 
import { isStrongPassword, isValidEmail, isValidURL } from '../helpers/validation';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import Container from '../components/Container';
import IconButton from '../components/IconButton';
import EditableAvatar from "../components/EditableAvatar.jsx";
import defaultAvatar from '../assets/default-avatar.jpg';
import { changePassword, getUserInfo } from "../services/UserService.js";
import alertService from "../helpers/alertService.js";

export default function EditUser() {

  const navigate = useNavigate();

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
        console.log('Datos a enviar:', generalData);
    }
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();

    let errs = {};

    if (!passwordData.currentPassword) errs.currentPassword = 'Debe introducir la contraseña actual';
    if (!isStrongPassword(passwordData.newPassword)) errs.newPassword = 'Contraseña débil. Debe tener mínimo 8 caracteres, mayúscula, minúscula, número y carácter especial.';
    if (passwordData.newPassword !== passwordData.confirmPassword) errs.confirmPassword = 'Las contraseñas no coinciden.';

    setErrors(errs);

    if (Object.keys(errs).length === 0) {
        const data = await changePassword(passwordData);
        if(data.message) {
          setPasswordData({
            currentPassword: '',
            newPassword: '',
            confirmPassword: ''
          })
          alertService({
            title: "Cambio de contraseña",
            text: data.message,
            icon: "success",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#098e00"
          })
        }
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
                initialUrl={avatarUrl || defaultAvatar}
                style={{width: '9rem', height: '9rem'}}
                onUploadSuccess={(data) => {
                  // Si el backend responde con la nueva URL del avatar:
                  if (data.avatarUrl) {
                    setAvatarUrl(data.avatarUrl);
                  }
                }}
            />
          </div>

        {/* Formulario de datos generales */}
        <form onSubmit={handleGeneralSubmit} noValidate>
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

