import React from 'react';
import UserAvatar from '../components/UserAvatar';
import LogoutButton from '../components/Logout';
import { useAuth } from '../context/AuthContext';
import '../css/Dashboard.css';
import defaultAvatar from '../assets/default-avatar.jpg';

function Dashboard() {

  const { userData, logout } = useAuth();

  if (!userData) return <p>Cargando datos del usuario...</p>;

  return (
    <div className="dashboard-container">
      <nav style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
      <div>
        {/* Aquí tus links o logo a la izquierda */}
        <a href="/">Inicio</a>
      </div>
      <LogoutButton onLogout={logout} />
      </nav>
      <UserAvatar
        url={userData.avatarUrl ? userData.avatarUrl : defaultAvatar}
        alt="Avatar de usuario"
      />
      <h1 className="dashboard-title">¡Bienvenido!</h1>
      <p className="dashboard-welcome"><strong>{userData.username}</strong>
      </p>
      <div className="dashboard-link">
        <a href={userData.web} target="_blank" rel="noopener noreferrer">
          Web
        </a>
      </div>
    </div>
  );
}

export default Dashboard;
