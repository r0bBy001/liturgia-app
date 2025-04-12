import apiRoutes from "../../../config/apiConfig";

export const getAllUsers = async () => {
  const response = await fetch(apiRoutes.users.getAll);
  if (!response.ok) {
    throw new Error("Error al obtener los usuarios");
  }
  return response.json();
};

export const getUserById = async (id) => {
  const response = await fetch(apiRoutes.users.getById(id));
  if (!response.ok) {
    throw new Error("Error al obtener el usuario");
  }
  return response.json();
};

export const createUser = async (formData) => {
  const response = await fetch(apiRoutes.users.create, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Error al crear el usuario");
  }
  return response.json();
};

export const updateUser = async (id, formData) => {
  const response = await fetch(apiRoutes.users.update(id), {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Error al actualizar el usuario");
  }
  return response.json();
};

export const deleteUser = async (id) => {
  const response = await fetch(apiRoutes.users.delete(id), {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Error al eliminar el usuario");
  }
};