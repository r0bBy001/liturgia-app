import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import apiRoutes from "../config/apiConfig"; // Importamos apiConfig

function IglesiaDetalle() {
  const { id } = useParams();
  const [iglesia, setIglesia] = useState(null);

  useEffect(() => {
    const fetchIglesia = async () => {
      try {
        const response = await fetch(apiRoutes.churches.getById(id)); // Usamos apiConfig para la ruta
        const data = await response.json();
        setIglesia(data);
      } catch (err) {
        console.error("Error al cargar iglesia:", err);
      }
    };

    fetchIglesia();
  }, [id]);

  if (!iglesia) {
    return <div className="text-center mt-20 text-gray-500">Cargando...</div>;
  }

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white mt-10 rounded-xl shadow-md">
      <img
        src={
          iglesia.portada
            ? `${apiRoutes.images.base}/iglesia/portadas/${iglesia.portada}` // Usamos apiConfig para la URL base de imágenes
            : iglesia.imagen
            ? `${apiRoutes.images.base}/iglesia/${iglesia.imagen}`
            : "https://via.placeholder.com/800x400?text=Sin+imagen"
        }
        alt={iglesia.nombre}
        className="w-full h-80 object-cover rounded-xl mb-6"
      />
      <h1 className="text-3xl font-bold text-gray-800 mb-2">{iglesia.nombre}</h1>
      <p className="text-gray-600 mb-4">
        {iglesia.descripcion || "Sin descripción."}
      </p>
      <p className="text-sm text-gray-500 mb-6">
        Ubicación: {iglesia.ciudad || "Ubicación no especificada"}
      </p>

      {/* Botón de volver */}
      <Link to="/" className="block text-center mt-6">
        <button className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md transition">
          ← Volver al inicio
        </button>
      </Link>
    </div>
  );
}

export default IglesiaDetalle;
