import apiRoutes from "../../../config/apiConfig";

export const getAllParents = async () => {
  const response = await fetch(apiRoutes.parents.getAll);
  if (!response.ok) {
    throw new Error("Error al obtener los padres");
  }
  return response.json();
};

export const getParentById = async (id) => {
  const response = await fetch(apiRoutes.parents.getById(id));
  if (!response.ok) {
    throw new Error("Error al obtener el padre");
  }
  return response.json();
};

export const createParent = async (formData) => {
  const response = await fetch(apiRoutes.parents.create, {
    method: "POST",
    body: formData, // FormData maneja automáticamente el encabezado Content-Type
  });
  if (!response.ok) {
    throw new Error("Error al crear el padre");
  }
  return response.json();
};

export const updateParent = async (id, formData) => {
  const response = await fetch(apiRoutes.parents.update(id), {
    method: "PUT",
    body: formData, // FormData maneja automáticamente el encabezado Content-Type
  });
  if (!response.ok) {
    throw new Error("Error al actualizar el padre");
  }
  return response.json();
};

export const deleteParent = async (id) => {
  const response = await fetch(apiRoutes.parents.delete(id), {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Error al eliminar el padre");
  }
};