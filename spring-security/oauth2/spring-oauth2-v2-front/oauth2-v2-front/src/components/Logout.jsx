import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons'; 

function LogoutButton({ onLogout }) {
  return (
    <button onClick={onLogout} style={{ cursor: 'pointer' }}>
      <FontAwesomeIcon icon={faRightFromBracket} />
    </button>
  );
}

export default LogoutButton;
