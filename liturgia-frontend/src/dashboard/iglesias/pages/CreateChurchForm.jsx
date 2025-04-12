import React, { useState } from "react";
import { createChurch } from "../services/churchService";

const CreateChurchForm = ({ onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    nombre: "",
    direccion: "",
    ciudad: "",
    telefono: "",
    correo: "",
    imagen: null,
    portada: null,
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (files) {
      setFormData({ ...formData, [name]: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData();
    Object.keys(formData).forEach((key) => {
      if (formData[key]) {
        data.append(key, formData[key]);
      }
    });

    try {
      await createChurch(data);
      alert("Iglesia creada con éxito");
      onSuccess(); // Actualiza la lista de iglesias
      onClose(); // Cierra el modal
    } catch (error) {
      console.error("Error al crear la iglesia:", error);
      alert("Error al crear la iglesia");
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
          <label className="block text-sm font-medium text-gray-700">Imagen</label>
          <input
            type="file"
            name="imagen"
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 bg-gray-800 text-white"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Portada</label>
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
        Crear Iglesia
      </button>
    </form>
  );
};

export default CreateChurchForm;