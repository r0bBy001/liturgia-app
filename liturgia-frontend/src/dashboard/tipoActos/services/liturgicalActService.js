import apiRoutes from "../../../config/apiConfig";

export const getAllLiturgicalActs = async () => {
  const response = await fetch(apiRoutes.liturgicalActs.getAll);
  if (!response.ok) {
    throw new Error("Error al obtener los tipos de actos litúrgicos");
  }
  return response.json();
};

export const getLiturgicalActById = async (id) => {
  const response = await fetch(apiRoutes.liturgicalActs.getById(id));
  if (!response.ok) {
    throw new Error("Error al obtener el tipo de acto litúrgico");
  }
  return response.json();
};

export const createLiturgicalAct = async (formData) => {
  const response = await fetch(apiRoutes.liturgicalActs.create, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Error al crear el tipo de acto litúrgico");
  }
  return response.json();
};

export const updateLiturgicalAct = async (id, formData) => {
  const response = await fetch(apiRoutes.liturgicalActs.update(id), {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Error al actualizar el tipo de acto litúrgico");
  }
  return response.json();
};

export const deleteLiturgicalAct = async (id) => {
  const response = await fetch(apiRoutes.liturgicalActs.delete(id), {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Error al eliminar el tipo de acto litúrgico");
  }
};