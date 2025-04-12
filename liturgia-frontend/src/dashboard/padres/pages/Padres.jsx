import React, { useEffect, useState } from "react";
import { getAllParents, deleteParent } from "../services/parentService";
import CreateParentForm from "./CreateParentForm";
import EditParentForm from "./EditParentForm";
import apiRoutes from "../../../config/apiConfig";

const Padres = () => {
  const [padres, setPadres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedParent, setSelectedParent] = useState(null);

  useEffect(() => {
    const fetchPadres = async () => {
      try {
        const data = await getAllParents();
        setPadres(data);
      } catch (error) {
        console.error("Error al cargar los padres:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPadres();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm("¿Estás seguro de que deseas eliminar este padre?")) {
      try {
        await deleteParent(id);
        setPadres(padres.filter((padre) => padre.id !== id));
        alert("Padre eliminado con éxito");
      } catch (error) {
        console.error("Error al eliminar el padre:", error);
        alert("Error al eliminar el padre");
      }
    }
  };

  const openEditModal = (padre) => {
    setSelectedParent(padre);
    setIsEditModalOpen(true);
  };

  const closeModals = () => {
    setIsCreateModalOpen(false);
    setIsEditModalOpen(false);
    setSelectedParent(null);
  };

  const refreshPadres = async () => {
    setLoading(true);
    try {
      const data = await getAllParents();
      setPadres(data);
    } catch (error) {
      console.error("Error al actualizar los padres:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-10 text-gray-500">Cargando padres...</div>;
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Gestión de Padres</h1>
        <button
          onClick={() => setIsCreateModalOpen(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
        >
          + Crear Padre
        </button>
      </div>

      {isCreateModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Crear Padre</h2>
            <CreateParentForm
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshPadres();
              }}
            />
          </div>
        </div>
      )}

      {isEditModalOpen && selectedParent && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Editar Padre</h2>
            <EditParentForm
              parent={selectedParent}
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshPadres();
              }}
            />
          </div>
        </div>
      )}

      {padres.length === 0 ? (
        <div className="text-center text-gray-500">No hay padres registrados.</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {padres.map((padre) => (
            <div
              key={padre.id}
              className="bg-white shadow-md rounded-lg p-4 border border-gray-200"
            >
              {padre.foto && (
                <img
                  src={`${apiRoutes.images.base}/padres/${padre.foto}`}
                  alt={`${padre.nombres} ${padre.apellidos}`}
                  className="w-full h-40 object-cover rounded-md mb-4"
                />
              )}
              <h2 className="text-lg font-bold text-gray-800">
                {padre.nombres} {padre.apellidos}
              </h2>
              <p className="text-sm text-gray-600">Descripción: {padre.descripcion}</p>
              <p className="text-sm text-gray-600">
                Iglesia: {padre.iglesia ? padre.iglesia.nombre : "Sin iglesia"}
              </p>
              <div className="flex justify-between items-center mt-4">
                <button
                  onClick={() => openEditModal(padre)}
                  className="bg-yellow-500 text-white px-3 py-1 rounded-md hover:bg-yellow-600 transition"
                >
                  Editar
                </button>
                <button
                  onClick={() => handleDelete(padre.id)}
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

export default Padres;