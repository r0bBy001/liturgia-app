import React from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import IglesiaDetalle from "./pages/IglesiaDetalle.jsx";
import AdminLogin from "./pages/AdminLogin.jsx";
import DashboardLayout from "./dashboard/layouts/DashboardLayout/DashboardLayout";
import Inicio from "./dashboard/inicio/pages/Inicio";
import Usuarios from "./dashboard/usuarios/pages/Usuarios";
import ListChurches from "./dashboard/iglesias/pages/ListChurches";
import Padres from "./dashboard/padres/pages/Padres";
import LiturgicalActs from "./dashboard/tipoActos/pages/LiturgicalActs";

const App = () => {
  return (
    <Routes>
      {/* Rutas principales */}
      <Route path="/" element={<Home />} />
      <Route path="/iglesias/:id" element={<IglesiaDetalle />} />
      <Route path="/admin" element={<AdminLogin />} />

      {/* Rutas del Dashboard */}
      <Route path="/dashboard" element={<DashboardLayout />}>
        <Route path="inicio" element={<Inicio />} />
        <Route path="usuarios" element={<Usuarios />} />
        <Route path="iglesias" element={<ListChurches />} />
        <Route path="padres" element={<Padres />} />
        <Route path="tipos-actos-liturgicos" element={<LiturgicalActs />} />
      </Route>
    </Routes>
  );
};

export default App;

