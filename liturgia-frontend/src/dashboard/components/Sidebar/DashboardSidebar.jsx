import React from "react";
import { Link } from "react-router-dom";
import { useUser } from "../../../context/UserContext";
import { FaChurch, FaUsers, FaHome, FaUser, FaSignOutAlt, FaListAlt, FaInfoCircle, FaCalendarAlt } from "react-icons/fa";

const DashboardSidebar = () => {
  const { user } = useUser();

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/admin";
  };

  return (
    <div className="w-64 bg-[#1B2A49] text-white h-screen shadow-md flex flex-col">
      <div className="h-16 flex items-center justify-center text-2xl font-bold border-b border-[#32486B] bg-[#24385B]">
        Admin Panel
      </div>

      <nav className="flex-1 px-4 py-6">
        <ul className="space-y-4 text-sm">
          <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
            <FaHome />
            <Link to="/dashboard/inicio">Inicio</Link>
          </li>

          {user?.role === "SUPERADMIN" && (
            <>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaChurch />
                <Link to="/dashboard/iglesias">Gestión de Iglesias</Link>
              </li>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaUsers />
                <Link to="/dashboard/usuarios">Gestión de Usuarios</Link>
              </li>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaUser />
                <Link to="/dashboard/padres">Gestión de Padres</Link>
              </li>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaListAlt />
                <Link to="/dashboard/tipos-actos-liturgicos">Tipos de Actos Litúrgicos</Link>
              </li>
            </>
          )}

          {user?.role === "ENCARGADO" && (
            <>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaChurch />
                <Link to="/dashboard/inicio">Mi Iglesia</Link>
              </li>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaInfoCircle />
                <Link to="/dashboard/informacion-institucional">Información Institucional</Link>
              </li>
              <li className="flex items-center gap-3 hover:text-[#F5CBA7]">
                <FaCalendarAlt />
                <Link to="/dashboard/actos-liturgicos">Calendario de Actos Litúrgicos</Link>
              </li>
            </>
          )}

          <li className="flex items-center gap-3 text-red-400 hover:text-red-600 mt-8">
            <FaSignOutAlt />
            <button onClick={handleLogout} className="text-left w-full">Cerrar Sesión</button>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default DashboardSidebar;