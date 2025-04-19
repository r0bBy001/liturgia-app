import { httpClient } from "../../../config/httpClient";

export const getAllChurches = async () => {
  return await httpClient("/iglesias", {
    method: "GET",
  });
};

export const getChurchById = async (id) => {
  return await httpClient(`/iglesias/${id}`, {
    method: "GET",
  });
};

export const createChurch = async (formData) => {
  // Enviar FormData directamente
  return await httpClient("/iglesias", {
    method: "POST",
    body: formData, // FormData se envía directamente
  });
};

export const updateChurch = async (id, formData) => {
  return await httpClient(`/iglesias/${id}`, {
    method: "PUT",
    body: formData, // FormData se envía directamente
  });
};

export const deleteChurch = async (id) => {
  return await httpClient(`/iglesias/${id}`, {
    method: "DELETE",
  });
};