import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import apiRoutes from "../config/apiConfig";
import Navbar from "../components/Navbar";
import { FaMapMarkerAlt, FaPhoneAlt, FaEnvelope, FaClock, FaUserTie } from "react-icons/fa";

function IglesiaDetalle() {
  const { id } = useParams();
  const [iglesia, setIglesia] = useState(null);
  const [padre, setPadre] = useState(null);

  useEffect(() => {
    fetch(apiRoutes.churches.getById(id))
      .then((res) => res.json())
      .then((data) => setIglesia(data))
      .catch((err) => console.error("Error al cargar iglesia:", err));

    fetch(`${apiRoutes.base}/padres/iglesia/${id}`)
      .then((res) => res.json())
      .then((data) => setPadre(data))
      .catch((err) => console.error("Error al cargar padre:", err));
  }, [id]);

  if (!iglesia) {
    return <div className="text-center mt-20 text-gray-500">Cargando...</div>;
  }

  return (
    <div className="bg-[#EAF2F8] min-h-screen text-[#2C3E50]">
      <Navbar />

      {/* Portada */}
      <div className="relative w-full h-[400px]">
        <img
          src={
            iglesia.portada
              ? `${apiRoutes.images.base}/iglesia/portadas/${iglesia.portada}`
              : "https://via.placeholder.com/1200x400?text=Sin+imagen"
          }
          alt="Portada"
          className="w-full h-full object-cover brightness-75"
        />
        <div className="absolute bottom-6 left-10 text-white">
          <h1 className="text-4xl font-bold">{iglesia.nombre}</h1>
          <p className="text-lg">{iglesia.ciudad}</p>
        </div>
        <div className="absolute -bottom-20 left-1/2 transform -translate-x-1/2 z-10">
          <img
            src={
              iglesia.imagen
                ? `${apiRoutes.images.base}/iglesia/${iglesia.imagen}`
                : "https://via.placeholder.com/150"
            }
            alt="Perfil"
            className="w-40 h-40 rounded-full border-4 border-white shadow-xl object-cover hover:scale-105 transition-transform duration-300"
          />
        </div>
      </div>

      <main className="max-w-6xl mx-auto px-6 pt-28 pb-12">
        <div className="grid md:grid-cols-3 gap-8">
          {/* COLUMNA IZQUIERDA */}
          <div className="md:col-span-2 space-y-6">
            <div className="bg-white rounded-xl shadow p-6">
              <h2 className="text-xl font-bold mb-2 text-[#D4AC0D]">Sobre la iglesia</h2>
              <p className="text-sm text-gray-700">
                {iglesia.descripcion || "Actualmente no contamos con una descripción detallada."}
              </p>

              <div className="mt-6">
                <h3 className="text-lg font-semibold text-[#D4AC0D] flex items-center gap-2">
                  <FaClock /> Horarios
                </h3>
                <p className="text-sm text-gray-700 mt-1">
                  Lunes a Viernes: 08:00 - 12:00 / 16:00 - 19:00<br />
                  Sábados y Domingos: 09:00 - 13:00 / 17:00 - 20:00
                </p>
              </div>
            </div>

            {/* Padre asignado */}
            {padre && (
              <div className="bg-white rounded-xl shadow p-6 flex items-center gap-6">
                <img
                  src={
                    padre.imagen
                      ? `${apiRoutes.images.base}/padre/${padre.imagen}`
                      : "https://via.placeholder.com/120"
                  }
                  alt="Padre"
                  className="w-24 h-24 rounded-full border-2 border-gray-300 object-cover"
                />
                <div>
                  <h2 className="text-lg font-bold text-[#D4AC0D] mb-1">Padre asignado</h2>
                  <p className="text-sm text-gray-700 flex items-center gap-2">
                    <FaUserTie className="text-[#2C3E50]" />
                    {padre.nombre} {padre.apellido}
                  </p>
                  <p className="text-sm text-gray-700 mt-1">Teléfono: {padre.telefono}</p>
                </div>
              </div>
            )}

            {/* Galería */}
            <div className="bg-white rounded-xl shadow p-6">
              <h2 className="text-lg font-bold text-[#D4AC0D] mb-4">Galería</h2>
              <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                {[1, 2, 3, 4].map((i) => (
                  <img
                    key={i}
                    src={`https://via.placeholder.com/300x200?text=Imagen+${i}`}
                    alt={`Imagen ${i}`}
                    className="rounded-lg object-cover w-full h-32"
                  />
                ))}
              </div>
            </div>
          </div>

          {/* Contacto iglesia */}
          <div className="bg-white rounded-xl shadow p-6">
            <h2 className="text-lg font-bold text-[#D4AC0D] mb-4">Contacto</h2>
            <p className="flex items-center gap-2 text-sm mb-2">
              <FaMapMarkerAlt /> {iglesia.ciudad || "Cusco"}
            </p>
            <p className="flex items-center gap-2 text-sm mb-2">
              <FaPhoneAlt /> {iglesia.telefono || "(084) 123456"}
            </p>
            <p className="flex items-center gap-2 text-sm mb-4">
              <FaEnvelope /> contacto@iglesiasdelcusco.pe
            </p>
            <button className="bg-[#64B374] hover:bg-[#4fa660] text-white text-sm px-4 py-2 rounded w-full">
              Enviar mensaje
            </button>
          </div>
        </div>

        {/* Cita inspiracional */}
        <div className="mt-10 text-center text-gray-600">
          <p className="text-md italic">
            "Cada iglesia es un pedazo de historia viva, un refugio para el alma y una joya de fe."
          </p>
        </div>
      </main>
    </div>
  );
}

export default IglesiaDetalle;
