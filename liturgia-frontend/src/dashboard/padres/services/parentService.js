import { httpClient } from "../../../config/httpClient";

export const getAllParents = async () => {
  return await httpClient("/padres", {
    method: "GET",
  });
};

export const getParentById = async (id) => {
  return await httpClient(`/padres/${id}`, {
    method: "GET",
  });
};

export const createParent = async (formData) => {
  // Enviar FormData directamente
  return await httpClient("/padres", {
    method: "POST",
    body: formData, // FormData se envía directamente
  });
};

export const updateParent = async (id, formData) => {
  return await httpClient(`/padres/${id}`, {
    method: "PUT",
    body: formData, // FormData se envía directamente
  });
};

export const deleteParent = async (id) => {
  const response = await httpClient(`/padres/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Error al eliminar el padre");
  }

  return response;
};