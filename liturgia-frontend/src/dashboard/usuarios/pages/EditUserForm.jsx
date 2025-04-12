import React, { useState, useEffect } from "react";
import { updateUser } from "../services/userService";
import { getAllChurches } from "../../iglesias/services/churchService";

const EditUserForm = ({ user, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    username: user.username || "",
    password: "",
    rol: user.rol || "",
    iglesiaId: user.iglesiaId || "",
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
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateUser(user.id, formData);
      alert("Usuario actualizado con éxito");
      onSuccess();
    } catch (error) {
      console.error("Error al actualizar el usuario:", error);
      alert("Error al actualizar el usuario");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium text-gray-700">Nombre de usuario</label>
        <input
          type="text"
          name="username"
          value={formData.username}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Contraseña</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Rol</label>
        <select
          name="rol"
          value={formData.rol}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2"
          required
        >
          <option value="">Seleccionar rol</option>
          <option value="SUPERADMIN">Superadmin</option>
          <option value="ENCARGADO">Encargado</option>
        </select>
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Iglesia</label>
        <select
          name="iglesiaId"
          value={formData.iglesiaId}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded px-3 py-2"
        >
          <option value="">Sin iglesia</option>
          {iglesias.map((iglesia) => (
            <option key={iglesia.id} value={iglesia.id}>
              {iglesia.nombre}
            </option>
          ))}
        </select>
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

export default EditUserForm;