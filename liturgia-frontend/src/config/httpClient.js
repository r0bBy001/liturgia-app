const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const httpClient = async (url, options = {}) => {
  const token = localStorage.getItem("token");

  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
    ...options.headers,
  };

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
    throw new Error("Error en la solicitud");
  }

  return response.json();
};