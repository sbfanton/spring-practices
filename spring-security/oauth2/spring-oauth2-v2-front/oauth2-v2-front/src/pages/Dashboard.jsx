import React from 'react';
import UserAvatar from '../components/UserAvatar';
import '../css/Dashboard.css';

function Dashboard({ user }) {
  return (
    <div className="dashboard-container">
      <UserAvatar
        url={user.avatarUrl}
        alt="Avatar de usuario"
      />
      <h1 className="dashboard-title">¡Bienvenido!</h1>
      <p className="dashboard-welcome"><strong>{user.username}</strong>
      </p>
      <div className="dashboard-link">
        <a href={user.web} target="_blank" rel="noopener noreferrer">
          Web
        </a>
      </div>
    </div>
  );
}

export default Dashboard;
