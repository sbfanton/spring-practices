import React from 'react';
import UserAvatar from '../components/UserAvatar';
import IconButton from '../components/IconButton';
import { useAuth } from '../context/AuthContext';
import '../css/Dashboard.css';
import defaultAvatar from '../assets/default-avatar.jpg';
import { faHome, faGlobe, faRightFromBracket, faUserEdit } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import Container from '../components/Container';
import { useAvatar } from "../context/AvatarContext.jsx";

function Dashboard() {

  const { userData, logout } = useAuth();
  const { avatarUrl } = useAvatar();
  const navigate = useNavigate();

  if (!userData) return <p>Cargando datos del usuario...</p>;

  const goToWeb = () => window.open(userData.web, '_blank', 'noopener,noreferrer');
  const goToHome = () => navigate('/');
  const goToEditUser = () => navigate('/editUser');

  const avatarStyle = {
    width: '6rem',
    height: '6rem',
    borderRadius: '50%', 
    objectFit: 'cover'
  }

  return (
    <Container className='container-dashboard'>
      <div className='dashboard-header'>
        <div className='profile'>
          <UserAvatar
            url={avatarUrl ? avatarUrl : defaultAvatar}
            alt="Avatar de usuario"
            style={avatarStyle}
          />
          <h1 className="dashboard-title"><strong>{userData.username}</strong></h1>
        </div>
        <nav>
          <IconButton handlerClick={goToHome} faIcon={faHome} classNameStyle='nav-button' label='Inicio' />
          <IconButton handlerClick={goToEditUser} faIcon={faUserEdit} classNameStyle='nav-button' label='Editar perfil' />
          <IconButton handlerClick={goToWeb} faIcon={faGlobe} classNameStyle='nav-button' label='Web' />
          <IconButton handlerClick={logout} faIcon={faRightFromBracket} classNameStyle='nav-button' label='Cerrar sesión' />
        </nav>
      </div>
    </Container>
  );
}

export default Dashboard;
