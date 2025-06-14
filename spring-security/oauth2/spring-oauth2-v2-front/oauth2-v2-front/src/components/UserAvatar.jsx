import { useState, useEffect } from 'react';

function UserAvatar({ url, alt = "Avatar de usuario", style = {} }) {
  const [imgSrc, setImgSrc] = useState(url);

  useEffect(() => {
    setImgSrc(url);
  }, [url]);

  return (
      <img
          src={imgSrc}
          alt={alt}
          onError={() => setImgSrc('/default-avatar.jpg')}
          style={{ ...style, borderRadius: '50%', objectFit: 'cover' }}
      />
  );
}

export default UserAvatar;