import React, { useState } from 'react';
import UserAvatar from './UserAvatar';
import { faPen } from '@fortawesome/free-solid-svg-icons';
import IconButton from "./IconButton.jsx";
import '../css/EditableAvatar.css';
import { useRef } from 'react';
import { changeAvatar } from "../services/UserService.js";
import { useAvatar } from "../context/AvatarContext";
import { useAuth } from "../context/AuthContext.jsx";

function EditableAvatar({ style = {} }) {
  const fileInputRef = useRef(null);
  const { avatarUrl, reloadAvatar } = useAvatar();
  const { setUserData } = useAuth();

  const handleFileChange = async (event) => {
    const file = event.target.files[0];
    const allowedTypes = ["image/jpeg", "image/jpg", "image/png"];
    if (!file || !allowedTypes.includes(file.type)) return;
    const data = await changeAvatar(file);
    if(data) {
      await showImage(data.message);
    }
  };

  const triggerFileInput = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click(); // simula clic en input
    }
  };

  const showImage = async (filename) => {
    setUserData(prev => ({
      ...prev,
      avatarUrl: filename
    }));

    setTimeout(() => {
      reloadAvatar();
    }, 0);
  }

  return (
      <div className="editable-avatar-container" style={{ position: 'relative', display: 'inline-block' }}>
        <UserAvatar url={avatarUrl} style={style} />

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
