import React from "react";
import { Navigate } from "react-router-dom";

const isTokenValid = () => {
  const token = localStorage.getItem("token");
  if (!token) return false;

  const payload = JSON.parse(atob(token.split(".")[1])); // Decodifica el payload del JWT
  const currentTime = Math.floor(Date.now() / 1000);

  return payload.exp > currentTime; // Verifica si el token ha expirado
};

const ProtectedRoute = ({ children }) => {
  if (!isTokenValid()) {
    localStorage.removeItem("token"); // Limpia el token si es inválido
    return <Navigate to="/admin" />;
  }

  return children;
};

export default ProtectedRoute;