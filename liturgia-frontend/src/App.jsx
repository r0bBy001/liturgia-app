import React from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import IglesiaDetalle from "./pages/IglesiaDetalle.jsx";
import ContactoIglesia from "./pages/ContactoIglesia.jsx";
import AdminLogin from "./pages/AdminLogin.jsx";
import Mapa from "./pages/Mapa.jsx";
import DashboardLayout from "./dashboard/layouts/DashboardLayout/DashboardLayout.jsx";
import Inicio from "./dashboard/inicio/pages/Inicio.jsx";
import Usuarios from "./dashboard/usuarios/pages/Usuarios.jsx";
import ListChurches from "./dashboard/iglesias/pages/ListChurches.jsx";
import Padres from "./dashboard/padres/pages/Padres.jsx";
import LiturgicalActs from "./dashboard/tipoActos/pages/LiturgicalActs.jsx";
import InformacionInstitucional from "./dashboard/informacionInstitucional/pages/InformacionInstitucional.jsx";
import LiturgicalEventCalendar from "./dashboard/ActosLiturgicos/pages/LiturgicalEventCalendar.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";

const App = () => {
  return (
    <Routes>
      {/* Rutas públicas */}
      <Route path="/" element={<Home />} />
      <Route path="/iglesias/:id" element={<IglesiaDetalle />} />
      <Route path="/iglesias/:id/contacto" element={<ContactoIglesia />} />
      <Route path="/contactos" element={<ContactoIglesia />} /> {/* ✅ Nueva ruta corregida */}
      <Route path="/admin" element={<AdminLogin />} />
      <Route path="/mapa" element={<Mapa />} />

      {/* Rutas del Dashboard (protegidas) */}
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute allowedRoles={["SUPERADMIN", "ENCARGADO"]}>
            <DashboardLayout />
          </ProtectedRoute>
        }
      >
        <Route path="inicio" element={<Inicio />} />
        <Route path="usuarios" element={<Usuarios />} />
        <Route path="iglesias" element={<ListChurches />} />
        <Route path="padres" element={<Padres />} />
        <Route path="tipos-actos-liturgicos" element={<LiturgicalActs />} />
        <Route path="actos-liturgicos" element={<LiturgicalEventCalendar />} />
        <Route path="informacion-institucional" element={<InformacionInstitucional />} />
      </Route>

      {/* Ruta para acceso denegado */}
      <Route path="/unauthorized" element={<div>Acceso Denegado</div>} />
    </Routes>
  );
};

export default App;
