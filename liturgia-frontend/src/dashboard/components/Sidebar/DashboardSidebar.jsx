import React from "react";
import { Link } from "react-router-dom";
import { useUser } from "../../../context/UserContext";

const DashboardSidebar = () => {
  const { user } = useUser();

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/admin";
  };

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
          {user?.role === "SUPERADMIN" && (
            <>
              <li>
                <Link to="/dashboard/iglesias" className="hover:text-blue-300">
                  Gestión de Iglesias
                </Link>
              </li>
              <li>
                <Link to="/dashboard/usuarios" className="hover:text-blue-300">
                  Gestión de Usuarios
                </Link>
              </li>
              <li>
                <Link to="/dashboard/padres" className="hover:text-blue-300">
                  Gestión de Padres
                </Link>
              </li>
              <li>
                <Link
                  to="/dashboard/tipos-actos-liturgicos"
                  className="hover:text-blue-300"
                >
                  Tipos de Actos Litúrgicos
                </Link>
              </li>
            </>
          )}
          {user?.role === "ENCARGADO" && (
            <>
              <li>
                <Link to="/dashboard/inicio" className="hover:text-blue-300">
                  Mi Iglesia
                </Link>
              </li>
              <li>
                <Link
                  to="/dashboard/informacion-institucional"
                  className="hover:text-blue-300"
                >
                  Información Institucional
                </Link>
              </li>
              <li>
                <Link
                  to="/dashboard/actos-liturgicos"
                  className="hover:text-blue-300"
                >
                  Calendario de Actos Litúrgicos
                </Link>
              </li>
            </>
          )}
          <li>
            <button
              onClick={handleLogout}
              className="w-full text-left hover:text-blue-300"
            >
              Cerrar Sesión
            </button>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default DashboardSidebar;