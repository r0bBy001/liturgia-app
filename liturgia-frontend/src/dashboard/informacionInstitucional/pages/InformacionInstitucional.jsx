import React, { useEffect, useState } from "react";
import { useUser } from "../../../context/UserContext";
import {
  getInformacionByIglesiaId,
  saveInformacion,
} from "../services/informacionService";

const InformacionInstitucional = () => {
  const { user } = useUser();
  const [informacion, setInformacion] = useState({
    descripcion: "",
    historia: "",
    mision: "",
    vision: "",
  });
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    const fetchInformacion = async () => {
      if (user?.iglesiaId) {
        try {
          const data = await getInformacionByIglesiaId(user.iglesiaId);
          setInformacion(data);
        } catch (error) {
          console.error("Error al obtener la información institucional:", error);
        }
      }
    };

    fetchInformacion();
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setInformacion({ ...informacion, [name]: value });
  };

  const handleSave = async () => {
    try {
      const payload = { ...informacion, iglesia: { id: user.iglesiaId } };
      await saveInformacion(payload);
      alert("Información guardada correctamente");
      setIsEditing(false);
    } catch (error) {
      console.error("Error al guardar la información institucional:", error);
      alert("Hubo un error al guardar la información");
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold text-gray-800 mb-4">
        Información Institucional
      </h1>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Descripción
          </label>
          <textarea
            name="descripcion"
            value={informacion.descripcion}
            onChange={handleChange}
            disabled={!isEditing}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-800"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Historia
          </label>
          <textarea
            name="historia"
            value={informacion.historia}
            onChange={handleChange}
            disabled={!isEditing}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-800"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Misión
          </label>
          <textarea
            name="mision"
            value={informacion.mision}
            onChange={handleChange}
            disabled={!isEditing}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-800"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Visión
          </label>
          <textarea
            name="vision"
            value={informacion.vision}
            onChange={handleChange}
            disabled={!isEditing}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-800"
          />
        </div>
        <button
          onClick={isEditing ? handleSave : () => setIsEditing(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition duration-300"
        >
          {isEditing ? "Guardar" : "Editar"}
        </button>
      </div>
    </div>
  );
};

export default InformacionInstitucional;