import apiRoutes from "../../../config/apiConfig";

export const login = async (credentials) => {
  const response = await fetch(apiRoutes.auth.login, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || "Error al iniciar sesión");
  }

  return response.json();
};