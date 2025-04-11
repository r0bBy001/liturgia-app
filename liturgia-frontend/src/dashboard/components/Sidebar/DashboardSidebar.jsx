import React from "react";
import { Link } from "react-router-dom";

const DashboardSidebar = () => {
  return (
    <div className="w-64 bg-blue-900 text-white h-full flex flex-col">
      <div className="p-4 text-2xl font-bold border-b border-blue-700">
        Dashboard
      </div>
      <nav className="flex-1 p-4">
        <ul className="space-y-4">
          <li>
            <Link to="/dashboard/inicio" className="hover:text-blue-300">
              Inicio
            </Link>
          </li>
          <li>
            <Link to="/dashboard/iglesias" className="hover:text-blue-300">
              Gestión de Iglesias
            </Link>
          </li>
          {/* Agrega más enlaces aquí si es necesario */}
        </ul>
      </nav>
    </div>
  );
};

export default DashboardSidebar;