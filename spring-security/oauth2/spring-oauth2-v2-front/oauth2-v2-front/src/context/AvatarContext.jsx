import { createContext, useContext, useEffect, useState } from "react";
import { getAvatar } from "../services/UserService";
import { useAuth } from "./AuthContext";
import defaultAvatar from '../assets/default-avatar.jpg';

const AvatarContext = createContext();

export const AvatarProvider = ({ children }) => {
  const { userData } = useAuth();
  const [avatarUrl, setAvatarUrl] = useState(null);

  const loadAvatar = async () => {
    if (userData?.avatarUrl && !userData.avatarUrl.startsWith("http")) {
      const url = await getAvatar(userData.avatarUrl);
      setAvatarUrl(url);
    } else {
      setAvatarUrl(userData?.avatarUrl || defaultAvatar);
    }
  };

  useEffect(() => {
    loadAvatar();
  }, [userData?.avatarUrl]);

  return (
      <AvatarContext.Provider value={{
        avatarUrl,
        setAvatarUrl,
        reloadAvatar: loadAvatar
      }}>
        {children}
      </AvatarContext.Provider>
  );
};

export const useAvatar = () => useContext(AvatarContext);
