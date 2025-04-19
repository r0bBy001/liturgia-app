import React, { useState, useEffect } from "react";
import { getChurchById, updateChurch } from "../services/churchService";

const EditChurchForm = ({ churchId, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    nombre: "",
    direccion: "",
    ciudad: "",
    telefono: "",
    correo: "",
    imagen: null,
    portada: null,
  });

  useEffect(() => {
    const fetchChurch = async () => {
      if (!churchId) {
        console.error("El ID de la iglesia es inválido");
        return;
      }
      try {
        const data = await getChurchById(churchId);
        setFormData({
          nombre: data.nombre || "",
          direccion: data.direccion || "",
          ciudad: data.ciudad || "",
          telefono: data.telefono || "",
          correo: data.correo || "",
          imagen: null,
          portada: null,
        });
      } catch (error) {
        console.error("Error al cargar la iglesia:", error);
      }
    };

    fetchChurch();
  }, [churchId]);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (files && files.length > 0) {
      setFormData({ ...formData, [name]: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!churchId) {
      alert("El ID de la iglesia es inválido");
      return;
    }

    const data = new FormData();
    Object.keys(formData).forEach((key) => {
      if (formData[key] !== null && formData[key] !== undefined) {
        data.append(key, formData[key]);
      }
    });

    try {
      await updateChurch(churchId, data);
      alert("Iglesia actualizada con éxito");
      onSuccess();
      onClose();
    } catch (error) {
      console.error("Error al actualizar la iglesia:", error);
      alert("Error al actualizar la iglesia");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Nombre</label>
          <input
            type="text"
            name="nombre"
            value={formData.nombre}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Dirección</label>
          <input
            type="text"
            name="direccion"
            value={formData.direccion}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Ciudad</label>
          <input
            type="text"
            name="ciudad"
            value={formData.ciudad}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Teléfono</label>
          <input
            type="text"
            name="telefono"
            value={formData.telefono}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Correo</label>
          <input
            type="email"
            name="correo"
            value={formData.correo}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Nueva Imagen</label>
          <input
            type="file"
            name="imagen"
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Nueva Portada</label>
          <input
            type="file"
            name="portada"
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
      </div>
      <button
        type="submit"
        className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
      >
        Actualizar Iglesia
      </button>
    </form>
  );
};

export default EditChurchForm;