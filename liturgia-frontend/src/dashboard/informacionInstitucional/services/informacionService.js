import { httpClient } from "../../../config/httpClient";

export const getInformacionByIglesiaId = async (iglesiaId) => {
  return await httpClient(`/informacion-institucional/iglesia/${iglesiaId}`, {
    method: "GET",
  });
};

export const saveInformacion = async (informacion) => {
  return await httpClient("/informacion-institucional", {
    method: "POST",
    body: JSON.stringify(informacion),
    headers: {
      "Content-Type": "application/json",
    },
  });
};