import React, { useState } from 'react';

function UserAvatar({ url, alt }) {
    const [imgSrc, setImgSrc] = useState(url);
  
    return (
      <img
        src={imgSrc}
        alt={alt}
        onError={() => setImgSrc('/default-avatar.png')}
        style={{ width: '250px', height: '250px', borderRadius: '50%', objectFit: 'cover' }}
      />
    );
  }

  export default UserAvatar;
  