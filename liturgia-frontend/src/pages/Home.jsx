import { useEffect, useState } from "react";
import { FaMapMarkerAlt, FaStar, FaSearch } from "react-icons/fa";
import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import apiRoutes from "../config/apiConfig";

function Home() {
  const [iglesias, setIglesias] = useState([]);
  const [busqueda, setBusqueda] = useState("");
  const [filtros, setFiltros] = useState({
    conDescripcion: false,
    conImagen: false,
    ordenAlfabetico: false,
  });

  useEffect(() => {
    fetch(apiRoutes.churches.getAll)
      .then((res) => res.json())
      .then((data) => setIglesias(data))
      .catch((error) => console.error("Error al obtener iglesias:", error));
  }, []);

  const iglesiasFiltradas = iglesias
    .filter((iglesia) =>
      iglesia.nombre.toLowerCase().includes(busqueda.toLowerCase())
    )
    .filter((iglesia) =>
      filtros.conDescripcion ? iglesia.descripcion?.trim() : true
    )
    .filter((iglesia) =>
      filtros.conImagen ? iglesia.portada?.trim() : true
    )
    .sort((a, b) =>
      filtros.ordenAlfabetico ? a.nombre.localeCompare(b.nombre) : 0
    );

  return (
    <>
      <Navbar />

      <div className="min-h-screen bg-gradient-to-b from-[#101916] to-[#355245] text-white py-10 px-4">
        <div className="max-w-7xl mx-auto flex flex-col md:flex-row gap-6">
          {/* Sidebar */}
          <Sidebar filtros={filtros} setFiltros={setFiltros} />

          {/* Contenido principal */}
          <div className="flex-1">
            <h1 className="text-4xl font-bold text-center text-[#E3E5D8] mb-8">
              Iglesias y Catedrales en Cusco
            </h1>

            {/* Buscador */}
            <div className="flex justify-center mb-10">
              <div className="relative w-full max-w-md">
                <input
                  type="text"
                  placeholder="Buscar iglesia por nombre..."
                  value={busqueda}
                  onChange={(e) => setBusqueda(e.target.value)}
                  className="w-full pl-10 pr-4 py-2 rounded-lg bg-[#101916] text-white placeholder-[#AFC1B6] border border-[#64B374] focus:outline-none focus:ring-2 focus:ring-[#64B374]"
                />
                <FaSearch className="absolute left-3 top-3 text-[#64B374]" />
              </div>
            </div>

            {/* Tarjetas de iglesias */}
            {iglesiasFiltradas.length > 0 ? (
              <div className="space-y-6">
                {iglesiasFiltradas.map((iglesia) => (
                  <div
                    key={iglesia.id}
                    className="bg-[#355245] rounded-xl shadow-lg hover:shadow-2xl transition-all duration-300 flex flex-col md:flex-row overflow-hidden"
                  >
                    <img
                      src={
                        iglesia.portada
                          ? `${apiRoutes.images.base}/iglesia/portadas/${iglesia.portada}`
                          : "https://via.placeholder.com/300x200?text=Sin+imagen"
                      }
                      alt={iglesia.nombre}
                      className="w-full md:w-1/3 h-60 object-cover"
                    />

                    <div className="p-6 flex flex-col justify-between w-full">
                      <div>
                        <h2 className="text-2xl font-bold text-white mb-2">
                          {iglesia.nombre}
                        </h2>
                        <p className="text-sm text-[#AFC1B6] flex items-center gap-2 mb-3">
                          <FaMapMarkerAlt className="text-[#64B374]" />
                          Cusco, Perú
                        </p>
                        <p className="text-[#E3E5D8] text-sm mb-4">
                          {iglesia.descripcion || "Descripción no disponible."}
                        </p>
                      </div>

                      <div className="flex items-center justify-between">
                        <div className="flex gap-1 text-yellow-400">
                          <FaStar />
                          <FaStar />
                          <FaStar />
                          <FaStar />
                          <FaStar className="text-gray-500" />
                        </div>

                        <Link to={`/iglesias/${iglesia.id}`}>
                          <button className="bg-[#64B374] hover:bg-[#4fa660] text-white px-4 py-1.5 rounded-md transition">
                            Ver más
                          </button>
                        </Link>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-center text-[#AFC1B6] mt-20">
                No se encontraron iglesias con ese filtro.
              </p>
            )}
          </div>
        </div>
      </div>
    </>
  );
}

export default Home;



