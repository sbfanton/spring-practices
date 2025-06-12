import React, { useState } from 'react';

function UserAvatar({ url, alt, styles }) {
    const [imgSrc, setImgSrc] = useState(url);
  
    return (
      <img
        src={imgSrc}
        alt={alt}
        onError={() => setImgSrc('/default-avatar.jpg')}
        style={styles}
      />
    );
  }

  export default UserAvatar;
  