import React from "react";
import { Outlet } from "react-router-dom";
import DashboardSidebar from "../../components/Sidebar/DashboardSidebar";

const DashboardLayout = () => {
  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <DashboardSidebar />

      {/* Área de contenido */}
      <div className="flex-1 p-6 overflow-y-auto">
        <Outlet /> {/* Aquí se renderizan las rutas hijas */}
      </div>
    </div>
  );
};

export default DashboardLayout;