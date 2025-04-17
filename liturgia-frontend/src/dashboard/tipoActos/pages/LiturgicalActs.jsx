import React, { useEffect, useState } from "react";
import {
  getAllLiturgicalActs,
  deleteLiturgicalAct,
} from "../services/liturgicalActService";
import CreateLiturgicalActForm from "./CreateLiturgicalActForm";
import EditLiturgicalActForm from "./EditLiturgicalActForm";

const LiturgicalActs = () => {
  const [acts, setActs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedAct, setSelectedAct] = useState(null);

  useEffect(() => {
    const fetchActs = async () => {
      try {
        const data = await getAllLiturgicalActs();
        setActs(data);
      } catch (error) {
        console.error("Error al cargar los tipos de actos litúrgicos:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchActs();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm("¿Estás seguro de que deseas eliminar este tipo de acto litúrgico?")) {
      try {
        await deleteLiturgicalAct(id);
        setActs(acts.filter((act) => act.id !== id));
        alert("Tipo de acto litúrgico eliminado con éxito");
      } catch (error) {
        console.error("Error al eliminar el tipo de acto litúrgico:", error);
        alert("Ocurrió un error al intentar eliminar el tipo de acto litúrgico.");
      }
    }
  };

  const openEditModal = (act) => {
    setSelectedAct(act);
    setIsEditModalOpen(true);
  };

  const closeModals = () => {
    setIsCreateModalOpen(false);
    setIsEditModalOpen(false);
    setSelectedAct(null);
  };

  const refreshActs = async () => {
    setLoading(true);
    try {
      const data = await getAllLiturgicalActs();
      setActs(data);
    } catch (error) {
      console.error("Error al actualizar los tipos de actos litúrgicos:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center mt-10 text-gray-500">Cargando tipos de actos litúrgicos...</div>;
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Gestión de Tipos de Actos Litúrgicos</h1>
        <button
          onClick={() => setIsCreateModalOpen(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
        >
          + Crear Tipo de Acto
        </button>
      </div>

      {isCreateModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Crear Tipo de Acto Litúrgico</h2>
            <CreateLiturgicalActForm
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshActs();
              }}
            />
          </div>
        </div>
      )}

      {isEditModalOpen && selectedAct && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Editar Tipo de Acto Litúrgico</h2>
            <EditLiturgicalActForm
              act={selectedAct}
              onClose={closeModals}
              onSuccess={() => {
                closeModals();
                refreshActs();
              }}
            />
          </div>
        </div>
      )}

      {acts.length === 0 ? (
        <div className="text-center text-gray-500">No hay tipos de actos litúrgicos registrados.</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {acts.map((act) => (
            <div
              key={act.id}
              className="bg-white shadow-md rounded-lg p-4 border border-gray-200"
            >
              <h2 className="text-lg font-bold text-gray-800">{act.nombre}</h2>
              <p className="text-sm text-gray-600">Descripción: {act.descripcion}</p>
              <div className="flex justify-between items-center mt-4">
                <button
                  onClick={() => openEditModal(act)}
                  className="bg-yellow-500 text-white px-3 py-1 rounded-md hover:bg-yellow-600 transition"
                >
                  Editar
                </button>
                <button
                  onClick={() => handleDelete(act.id)}
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

export default LiturgicalActs;