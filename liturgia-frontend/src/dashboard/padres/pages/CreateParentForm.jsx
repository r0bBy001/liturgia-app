import React, { useState, useEffect } from "react";
import { createParent } from "../services/parentService";
import { getAllChurches } from "../../iglesias/services/churchService";

const CreateParentForm = ({ onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    nombres: "",
    apellidos: "",
    descripcion: "",
    iglesiaId: "",
    foto: null,
  });
  const [iglesias, setIglesias] = useState([]);

  useEffect(() => {
    const fetchIglesias = async () => {
      try {
        const data = await getAllChurches();
        setIglesias(data);
      } catch (error) {
        console.error("Error al cargar las iglesias:", error);
      }
    };

    fetchIglesias();
  }, []);

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
      await createParent(data);
      alert("Padre creado con éxito");
      onSuccess();
    } catch (error) {
      console.error("Error al crear el padre:", error);
      alert("Error al crear el padre");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium text-gray-700">Nombres</label>
        <input
          type="text"
          name="nombres"
          value={formData.nombres}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2 text-black"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Apellidos</label>
        <input
          type="text"
          name="apellidos"
          value={formData.apellidos}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2 text-black"
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
      <div>
        <label className="block text-sm font-medium text-gray-700">Iglesia</label>
        <select
          name="iglesiaId"
          value={formData.iglesiaId}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2 text-black bg-white"
          required
        >
          <option value="">Seleccionar iglesia</option>
          {iglesias.map((iglesia) => (
            <option key={iglesia.id} value={iglesia.id}>
              {iglesia.nombre}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Foto</label>
        <input
          type="file"
          name="foto"
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
          Crear Padre
        </button>
      </div>
    </form>
  );
};

export default CreateParentForm;