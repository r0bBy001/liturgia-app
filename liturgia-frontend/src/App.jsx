import React from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import IglesiaDetalle from "./pages/IglesiaDetalle.jsx";
import ContactoIglesia from "./pages/ContactoIglesia.jsx"; // 👉 NUEVO: Página de contacto individual
import AdminLogin from "./pages/AdminLogin.jsx";
import Mapa from "./pages/Mapa.jsx"; // 👉 Página del mapa
import DashboardLayout from "./dashboard/layouts/DashboardLayout/DashboardLayout";
import Inicio from "./dashboard/inicio/pages/Inicio";
import Usuarios from "./dashboard/usuarios/pages/Usuarios";
import ListChurches from "./dashboard/iglesias/pages/ListChurches";

const App = () => {
  return (
    <Routes>
      {/* Rutas principales */}
      <Route path="/" element={<Home />} />
      <Route path="/iglesias/:id" element={<IglesiaDetalle />} />
      <Route path="/iglesias/:id/contacto" element={<ContactoIglesia />} /> {/* ✅ Nueva ruta de contacto */}
      <Route path="/admin" element={<AdminLogin />} />
      <Route path="/mapa" element={<Mapa />} />

      {/* Rutas del Dashboard */}
      <Route path="/dashboard" element={<DashboardLayout />}>
        <Route path="inicio" element={<Inicio />} />
        <Route path="usuarios" element={<Usuarios />} />
        <Route path="iglesias" element={<ListChurches />} />
      </Route>
    </Routes>
  );
};

export default App;
