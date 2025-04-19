import React, { useEffect, useState } from "react";
import { useUser } from "../../../context/UserContext";
import { getChurchById } from "../../iglesias/services/churchService";

const IMAGE_BASE_URL = "http://localhost:8080/uploads";

const Inicio = () => {
  const { user } = useUser();
  const [iglesia, setIglesia] = useState(null);

  useEffect(() => {
    const fetchIglesia = async () => {
      if (user?.role === "ENCARGADO" && user?.iglesiaId) {
        try {
          const data = await getChurchById(user.iglesiaId);
          setIglesia(data);
        } catch (error) {
          console.error("Error al obtener los datos de la iglesia:", error);
        }
      }
    };

    fetchIglesia();
  }, [user]);

  if (user?.role === "SUPERADMIN") {
    return (
      <div>
        <h1>Bienvenido al Dashboard</h1>
      </div>
    );
  }

  if (user?.role === "ENCARGADO") {
    return (
      <div className="p-6 flex flex-col items-center">
        <h1 className="text-3xl font-bold text-gray-800 text-center mb-6">
          Iglesia de {iglesia?.nombre || "Cargando..."}
        </h1>
        {iglesia?.portada || iglesia?.imagen ? (
          <img
            src={
              iglesia.portada
                ? `${IMAGE_BASE_URL}/iglesia/portadas/${iglesia.portada}`
                : `${IMAGE_BASE_URL}/iglesia/${iglesia.imagen}`
            }
            alt={`Foto de ${iglesia?.nombre}`}
            className="w-full max-w-2xl h-auto rounded-lg shadow-md"
          />
        ) : (
          <p className="text-gray-500 mt-4">No hay imagen disponible para esta iglesia.</p>
        )}
      </div>
    );
  }

  return <div>Cargando...</div>;
};

export default Inicio;