import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import '../css/IconButton.css';

function IconButton({ handlerClick, faIcon, classNameStyle = 'nav-button', label }) {
  return (
    <div className="icon-button-wrapper">
      <button onClick={handlerClick} className={classNameStyle}>
        <FontAwesomeIcon icon={faIcon} />
      </button>
      {label && <span className="tooltip">{label}</span>}
    </div>
  );
}

export default IconButton;