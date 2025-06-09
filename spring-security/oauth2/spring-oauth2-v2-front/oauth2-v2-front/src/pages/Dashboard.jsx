import React from 'react';
import UserAvatar from '../components/UserAvatar';
import IconButton from '../components/IconButton';
import { useAuth } from '../context/AuthContext';
import '../css/Dashboard.css';
import defaultAvatar from '../assets/default-avatar.jpg';
import { faHome, faGlobe, faRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';

function Dashboard() {

  const { userData, logout } = useAuth();
  const navigate = useNavigate();

  if (!userData) return <p>Cargando datos del usuario...</p>;

  const goToWeb = () => window.open(userData.web, '_blank', 'noopener,noreferrer');
  const goToHome = () => navigate('/');

  return (
    <div className="dashboard-container">
      <UserAvatar
        url={userData.avatarUrl ? userData.avatarUrl : defaultAvatar}
        alt="Avatar de usuario"
      />
      <h1 className="dashboard-title">¡Bienvenido!</h1>
      <p className="dashboard-welcome"><strong>{userData.username}</strong>
      </p>
      <nav>
        <IconButton handlerClick={goToHome} faIcon={faHome} classNameStyle='nav-button' />
        <IconButton handlerClick={goToWeb} faIcon={faGlobe} classNameStyle='nav-button' />
        <IconButton handlerClick={logout} faIcon={faRightFromBracket} classNameStyle='nav-button' />
      </nav>
    </div>
  );
}

export default Dashboard;
