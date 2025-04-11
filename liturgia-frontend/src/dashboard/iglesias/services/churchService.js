import apiRoutes from "../../../config/apiConfig";

export const getAllChurches = async () => {
  const response = await fetch(apiRoutes.churches.getAll);
  if (!response.ok) {
    throw new Error("Error al obtener las iglesias");
  }
  return response.json();
};

export const getChurchById = async (id) => {
  const response = await fetch(apiRoutes.churches.getById(id));
  if (!response.ok) {
    throw new Error("Error al obtener la iglesia");
  }
  return response.json();
};

export const createChurch = async (formData) => {
  const response = await fetch(apiRoutes.churches.create, {
    method: "POST",
    body: formData,
  });
  if (!response.ok) {
    throw new Error("Error al crear la iglesia");
  }
  return response.json();
};

export const updateChurch = async (id, formData) => {
  const response = await fetch(apiRoutes.churches.update(id), {
    method: "PUT",
    body: formData,
  });
  if (!response.ok) {
    throw new Error("Error al actualizar la iglesia");
  }
  return response.json();
};

export const deleteChurch = async (id) => {
  const response = await fetch(apiRoutes.churches.delete(id), {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Error al eliminar la iglesia");
  }
};