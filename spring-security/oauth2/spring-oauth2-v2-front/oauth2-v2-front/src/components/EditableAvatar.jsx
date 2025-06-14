import React, { useState } from 'react';
import UserAvatar from './UserAvatar';
import { faPen } from '@fortawesome/free-solid-svg-icons';
import IconButton from "./IconButton.jsx";
import '../css/EditableAvatar.css';
import { useRef } from 'react';

function EditableAvatar({ initialUrl, style = {}, onUploadSuccess }) {
  const [imgSrc, setImgSrc] = useState(initialUrl);
  const fileInputRef = useRef(null);

  const handleFileChange = async (event) => {
    const file = event.target.files[0];
    if (!file) return;

    setImgSrc(URL.createObjectURL(file));

    const formData = new FormData();
    formData.append('avatar', file);

    try {
      const response = await fetch('/api/user/avatar', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) throw new Error('Error al subir el avatar');
      const data = await response.json();
      if (onUploadSuccess) onUploadSuccess(data);

    } catch (error) {
      console.error('Error al subir el avatar:', error);
    }
  };

  const triggerFileInput = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click(); // simula clic en input
    }
  };

  return (
      <div className="editable-avatar-container" style={{ position: 'relative', display: 'inline-block' }}>
        <UserAvatar url={imgSrc} style={style} />

        <IconButton
            handlerClick={triggerFileInput}
            faIcon={faPen}
            classNameStyle="edit-avatar-icon"
        />

        <input
            type="file"
            id="avatar-upload"
            accept="image/*"
            onChange={handleFileChange}
            style={{ display: 'none' }}
            ref={fileInputRef}
        />
      </div>
  );
}

export default EditableAvatar;
