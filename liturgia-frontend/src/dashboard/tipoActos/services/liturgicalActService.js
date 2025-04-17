import { httpClient } from "../../../config/httpClient";

export const getAllLiturgicalActs = async () => {
  return await httpClient("/tipos-actos", {
    method: "GET",
  });
};

export const getLiturgicalActById = async (id) => {
  return await httpClient(`/tipos-actos/${id}`, {
    method: "GET",
  });
};

export const createLiturgicalAct = async (data) => {
  return await httpClient("/tipos-actos", {
    method: "POST",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });
};

export const updateLiturgicalAct = async (id, data) => {
  return await httpClient(`/tipos-actos/${id}`, {
    method: "PUT",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });
};

export const deleteLiturgicalAct = async (id) => {
  const response = await httpClient(`/tipos-actos/${id}`, {
    method: "DELETE",
  });

  if (response === null || !response.ok) {
    throw new Error("Error al eliminar el tipo de acto litúrgico");
  }

  return response;
};