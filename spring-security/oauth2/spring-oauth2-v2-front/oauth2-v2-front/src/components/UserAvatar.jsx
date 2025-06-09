import React, { useState } from 'react';

function UserAvatar({ url, alt }) {
    const [imgSrc, setImgSrc] = useState(url);
  
    return (
      <img
        src={imgSrc}
        alt={alt}
        onError={() => setImgSrc('/default-avatar.jpg')}
        style={{ width: '9rem', height: '9rem', borderRadius: '50%', objectFit: 'cover' }}
      />
    );
  }

  export default UserAvatar;
  