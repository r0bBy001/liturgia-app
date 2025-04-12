import React, { useEffect, useState } from "react";
import { getAllUsers, deleteUser } from "../services/userService";
import CreateUserForm from "./CreateUserForm";
import EditUserForm from "./EditUserForm";

const Usuarios = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const data = await getAllUsers();
        setUsuarios(data);
      } catch (error) {
        console.error("Error al cargar los usuarios:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchUsuarios();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm("¿Estás seguro de que deseas eliminar este usuario?")) {
      try {
        await deleteUser(id);
        setUsuarios(usuarios.filter((usuario) => usuario.id !== id));
        alert("Usuario eliminado con éxito");
      } catch (error) {
        console.error("Error al eliminar el usuario:", error);
        alert("Error al eliminar el usuario");
      }
    }
  };

  const openEditModal = (usuario) => {
    setSelectedUser(usuario);
    setIsEditModalOpen(true);
  };

  const closeModals = () => {
    setIsCreateModalOpen(false);
    setIsEditModalOpen(false);
    setSelectedUser(null);
  };

  const refreshUsuarios = async () => {
    setLoading(true);
    try {
      const data = await getAllUsers();
      setUsuarios(data);
    } catch (error) {
      console.error("Error al actualizar los usuarios:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-10 text-gray-500">Cargando usuarios...</div>;
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Gestión de Usuarios</h1>
        <button
          onClick={() => setIsCreateModalOpen(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
        >
          + Crear Usuario
        </button>
      </div>

      {isCreateModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Crear Usuario</h2>
            <CreateUserForm
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshUsuarios();
              }}
            />
          </div>
        </div>
      )}

      {isEditModalOpen && selectedUser && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Editar Usuario</h2>
            <EditUserForm
              user={selectedUser}
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshUsuarios();
              }}
            />
          </div>
        </div>
      )}

      {usuarios.length === 0 ? (
        <div className="text-center text-gray-500">No hay usuarios registrados.</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {usuarios.map((usuario) => (
            <div
              key={usuario.id}
              className="bg-white shadow-md rounded-lg p-4 border border-gray-200"
            >
              <h2 className="text-lg font-bold text-gray-800">{usuario.username}</h2>
              <p className="text-sm text-gray-600">Rol: {usuario.rol}</p>
              <div className="flex justify-between items-center mt-4">
                <button
                  onClick={() => openEditModal(usuario)}
                  className="bg-yellow-500 text-white px-3 py-1 rounded-md hover:bg-yellow-600 transition"
                >
                  Editar
                </button>
                <button
                  onClick={() => handleDelete(usuario.id)}
                  className="bg-red-500 text-white px-3 py-1 rounded-md hover:bg-red-600 transition"
                >
                  Eliminar
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Usuarios;