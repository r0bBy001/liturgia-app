import React, { useState } from "react";
import { updateLiturgicalAct } from "../services/liturgicalActService";

const EditLiturgicalActForm = ({ act, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    nombre: act.nombre || "",
    descripcion: act.descripcion || "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateLiturgicalAct(act.id, formData);
      alert("Tipo de acto litúrgico actualizado con éxito");
      onSuccess();
    } catch (error) {
      console.error("Error al actualizar el tipo de acto litúrgico:", error);
      alert("Error al actualizar el tipo de acto litúrgico");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium text-gray-700">Nombre</label>
        <input
          type="text"
          name="nombre"
          value={formData.nombre}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2 text-black"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Descripción</label>
        <textarea
          name="descripcion"
          value={formData.descripcion}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2 text-black"
        />
      </div>
      <div className="flex justify-end space-x-4">
        <button
          type="button"
          onClick={onClose}
          className="bg-gray-300 text-gray-700 px-4 py-2 rounded hover:bg-gray-400 transition"
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
        >
          Guardar Cambios
        </button>
      </div>
    </form>
  );
};

export default EditLiturgicalActForm;