import React, { useEffect, useState } from "react";
import { getAllChurches, deleteChurch } from "../services/churchService";
import { Link } from "react-router-dom";
import CreateChurchForm from "./CreateChurchForm";
import EditChurchForm from "./EditChurchForm";

const IMAGE_BASE_URL = "http://localhost:8080/uploads";

const ListChurches = () => {
  const [iglesias, setIglesias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedChurch, setSelectedChurch] = useState(null);

  useEffect(() => {
    const fetchIglesias = async () => {
      try {
        const data = await getAllChurches();
        setIglesias(data);
      } catch (error) {
        console.error("Error al cargar las iglesias:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchIglesias();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm("¿Estás seguro de que deseas eliminar esta iglesia?")) {
      try {
        await deleteChurch(id);
        setIglesias(iglesias.filter((iglesia) => iglesia.id !== id));
        alert("Iglesia eliminada con éxito");
      } catch (error) {
        console.error("Error al eliminar la iglesia:", error);
        alert("Ocurrió un error al intentar eliminar la iglesia.");
      }
    }
  };

  const openEditModal = (iglesia) => {
    setSelectedChurch(iglesia);
    setIsEditModalOpen(true);
  };

  const closeModals = () => {
    setIsCreateModalOpen(false);
    setIsEditModalOpen(false);
    setSelectedChurch(null);
  };

  const refreshChurches = async () => {
    setLoading(true);
    try {
      const data = await getAllChurches();
      setIglesias(data);
    } catch (error) {
      console.error("Error al actualizar las iglesias:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-10 text-gray-500">Cargando iglesias...</div>;
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Gestión de Iglesias</h1>
        <button
          onClick={() => setIsCreateModalOpen(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
        >
          + Crear Iglesia
        </button>
      </div>

      {/* Modal para crear iglesia */}
      {isCreateModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Crear Iglesia</h2>
            <CreateChurchForm
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshChurches();
              }}
            />
          </div>
        </div>
      )}

      {/* Modal para editar iglesia */}
      {isEditModalOpen && selectedChurch && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Editar Iglesia</h2>
            <EditChurchForm
              church={selectedChurch}
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshChurches();
              }}
            />
          </div>
        </div>
      )}

      {iglesias.length === 0 ? (
        <div className="text-center text-gray-500">No hay iglesias registradas.</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {iglesias.map((iglesia) => (
            <div
              key={iglesia.id}
              className="bg-white shadow-md rounded-lg p-4 border border-gray-200"
            >
              <img
                src={
                  iglesia.portada
                    ? `${IMAGE_BASE_URL}/iglesia/portadas/${iglesia.portada}`
                    : iglesia.imagen
                    ? `${IMAGE_BASE_URL}/iglesia/${iglesia.imagen}`
                    : "https://via.placeholder.com/150?text=Sin+Imagen"
                }
                alt={iglesia.nombre}
                className="w-full h-40 object-cover rounded-md mb-4"
              />
              <h2 className="text-lg font-bold text-gray-800">{iglesia.nombre}</h2>
              <p className="text-sm text-gray-600">{iglesia.direccion}</p>
              <p className="text-sm text-gray-600">{iglesia.ciudad}</p>
              <div className="flex justify-between items-center mt-4">
                <button
                  onClick={() => openEditModal(iglesia)}
                  className="bg-yellow-500 text-white px-3 py-1 rounded-md hover:bg-yellow-600 transition"
                >
                  Editar
                </button>
                <button
                  onClick={() => handleDelete(iglesia.id)}
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

export default ListChurches;