import { httpClient } from "../../../config/httpClient";

export const getAllUsers = async () => {
  return await httpClient("/usuarios", {
    method: "GET",
  });
};

export const getUserById = async (id) => {
  return await httpClient(`/usuarios/${id}`, {
    method: "GET",
  });
};

export const createUser = async (formData) => {
  return await httpClient("/usuarios", {
    method: "POST",
    body: JSON.stringify(formData),
  });
};

export const updateUser = async (id, formData) => {
  return await httpClient(`/usuarios/${id}`, {
    method: "PUT",
    body: JSON.stringify(formData),
  });
};

export const deleteUser = async (id) => {
  return await httpClient(`/usuarios/${id}`, {
    method: "DELETE",
  });
};