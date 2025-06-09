import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../css/IconButton.css';

function IconButton({ handlerClick, faIcon, classNameStyle = 'icon-button' }) {
  return (
    <button onClick={handlerClick} className={classNameStyle} >
      <FontAwesomeIcon icon={faIcon} />
    </button>
  );
}

export default IconButton;
