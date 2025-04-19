import React, { createContext, useContext, useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";

// Crear y exportar el contexto
export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const loadUserFromToken = () => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decoded = jwtDecode(token);
        const currentTime = Math.floor(Date.now() / 1000);
        if (decoded.exp && decoded.exp < currentTime) {
          localStorage.removeItem("token");
          setUser(null);
        } else {
          setUser({
            username: decoded.sub,
            role: decoded.role,
            iglesiaId: decoded.iglesiaId || null,
          });
        }
      } catch (error) {
        localStorage.removeItem("token");
        setUser(null);
      }
    } else {
      setUser(null);
    }
  };

  useEffect(() => {
    loadUserFromToken();
    window.addEventListener("storage", loadUserFromToken);
    return () => {
      window.removeEventListener("storage", loadUserFromToken);
    };
  }, []);

  return <UserContext.Provider value={{ user, loadUserFromToken }}>{children}</UserContext.Provider>;
};

export const useUser = () => useContext(UserContext);