import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { FaSearch, FaUser, FaGlobe, FaChurch } from "react-icons/fa";
import apiRoutes from "../config/apiConfig";

function Navbar() {
  const [busqueda, setBusqueda] = useState("");
  const [resultados, setResultados] = useState([]);
  const [iglesias, setIglesias] = useState([]);

  useEffect(() => {
    fetch(apiRoutes.churches.getAll)
      .then((res) => res.json())
      .then((data) => setIglesias(data))
      .catch((error) => console.error("Error al cargar iglesias:", error));
  }, []);

  useEffect(() => {
    if (busqueda.trim() === "") {
      setResultados([]);
      return;
    }

    const resultadosFiltrados = iglesias.filter((iglesia) =>
      iglesia.nombre.toLowerCase().includes(busqueda.toLowerCase())
    );
    setResultados(resultadosFiltrados);
  }, [busqueda, iglesias]);

  return (
    <header className="bg-white shadow-md relative z-50">
      <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">
        <Link to="/" className="flex items-center gap-2 text-2xl font-bold text-[#2C3E50]">
          <FaChurch className="text-[#F5CBA7]" />
          Iglesias del Cusco
        </Link>

        <nav className="space-x-6 text-[#2C3E50] hidden md:flex items-center">
          <Link to="/" className="hover:text-[#F5CBA7]">Inicio</Link>
          <Link to="/mapa" className="hover:text-[#F5CBA7]">Mapa</Link>
          <Link to="/foros" className="hover:text-[#F5CBA7]">Foros</Link>
          <Link to="/contactos" className="hover:text-[#F5CBA7]">Contactos</Link>
 {/* 👈 Solo agregamos este enlace */}
        </nav>

        {/* Buscador */}
        <div className="relative w-[200px] md:w-[250px]">
          <div className="flex items-center bg-white border rounded-full px-3 py-1 shadow-sm">
            <FaSearch className="text-gray-400 mr-2" />
            <input
              type="text"
              value={busqueda}
              onChange={(e) => setBusqueda(e.target.value)}
              placeholder="Buscar..."
              className="w-full bg-transparent focus:outline-none text-sm"
            />
          </div>
          {resultados.length > 0 && (
            <div className="absolute mt-2 w-full bg-white border rounded-md shadow-lg max-h-64 overflow-y-auto z-50">
              {resultados.map((item) => (
                <Link
                  key={item.id}
                  to={`/iglesias/${item.id}`}
                  className="block px-4 py-2 hover:bg-[#F5CBA7] hover:text-white text-sm transition"
                  onClick={() => setBusqueda("")}
                >
                  {item.nombre}
                </Link>
              ))}
            </div>
          )}
        </div>

        {/* Botones */}
        <div className="flex items-center gap-4 ml-4">
          <div className="flex items-center text-[#2C3E50] text-sm">
            <FaGlobe className="mr-1" />
            ES
          </div>
          <button className="flex items-center bg-[#F5CBA7] hover:bg-[#F0B27A] text-white px-4 py-1.5 rounded-md text-sm transition">
            <FaUser className="mr-2" /> Iniciar sesión
          </button>
        </div>
      </div>
    </header>
  );
}

export default Navbar;
