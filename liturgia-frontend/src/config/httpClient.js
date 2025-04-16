const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const httpClient = async (url, options = {}) => {
  const token = localStorage.getItem("token");
  console.log("Token:", token); // Verifica si el token está presente

  const headers = {
    Authorization: `Bearer ${token}`, // Agregar el token al encabezado
    ...options.headers,
  };

  // Si el cuerpo es un FormData, elimina el encabezado Content-Type
  if (options.body instanceof FormData) {
    delete headers["Content-Type"];
  } else {
    headers["Content-Type"] = "application/json"; // Usar JSON por defecto si no es FormData
  }

  const response = await fetch(`${API_BASE_URL}${url}`, {
    ...options,
    headers,
  });

  // Manejar errores de autenticación
  if (response.status === 401) {
    localStorage.removeItem("token");
    window.location.href = "/admin";
    throw new Error("No autorizado");
  }

  // Manejar otros errores de la solicitud
  if (!response.ok) {
    const errorMessage = await response.text(); // Obtener el mensaje de error del backend si está disponible
    throw new Error(errorMessage || "Error en la solicitud");
  }

  // Manejar respuestas sin cuerpo (204 No Content)
  if (response.status === 204) {
    return null; // Devuelve null si no hay contenido
  }

  // Manejar respuestas con cuerpo
  try {
    return await response.json(); // Intenta convertir la respuesta a JSON
  } catch (error) {
    console.warn("No se pudo convertir la respuesta a JSON:", error);
    return null; // Devuelve null si no se puede convertir a JSON
  }
};