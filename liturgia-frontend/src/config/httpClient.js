const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const httpClient = async (url, options = {}) => {
  const token = localStorage.getItem("token");

  const headers = {
    Authorization: `Bearer ${token}`,
    ...options.headers,
  };

  if (options.body instanceof FormData) {
    delete headers["Content-Type"];
  } else {
    headers["Content-Type"] = "application/json";
  }

  const response = await fetch(`${API_BASE_URL}${url}`, {
    ...options,
    headers,
  });

  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/admin";
    throw new Error("No autorizado");
  }

  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage || "Error en la solicitud");
  }

  if (response.status === 204) {
    return null;
  }

  try {
    return await response.json();
  } catch (error) {
    console.warn("No se pudo convertir la respuesta a JSON:", error);
    return null;
  }
};