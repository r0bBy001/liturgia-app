import { httpClient } from '../../../config/httpClient';

// Obtener todos los actos litúrgicos
export const getAllLiturgicalEvents = async (churchId = null) => {
  try {
    // Si hay un churchId, usar el endpoint específico para esa iglesia
    const url = churchId ? `/actos-liturgicos/iglesia/${churchId}` : '/actos-liturgicos';
    return await httpClient(url, {
      method: "GET",
    });
  } catch (error) {
    console.error('Error al obtener actos litúrgicos:', error);
    throw error;
  }
};

// Obtener actos litúrgicos por rango de fechas
export const getLiturgicalEventsByDateRange = async (startDate, endDate, churchId = null) => {
  try {
    // Formatear fechas en formato ISO
    const formattedStartDate = startDate.toISOString();
    const formattedEndDate = endDate.toISOString();
    
    let url;
    if (churchId) {
      // Si hay un churchId, usar endpoint con iglesia y rango
      url = `/actos-liturgicos/iglesia/${churchId}/rango?inicio=${formattedStartDate}&fin=${formattedEndDate}`;
    } else {
      // Si no hay churchId, usar solo rango
      url = `/actos-liturgicos/rango?inicio=${formattedStartDate}&fin=${formattedEndDate}`;
    }
    
    return await httpClient(url, {
      method: "GET",
    });
  } catch (error) {
    console.error('Error al obtener actos litúrgicos por rango de fechas:', error);
    throw error;
  }
};

// Obtener un acto litúrgico por ID
export const getLiturgicalEventById = async (id) => {
  try {
    return await httpClient(`/actos-liturgicos/${id}`, {
      method: "GET",
    });
  } catch (error) {
    console.error(`Error al obtener acto litúrgico con ID ${id}:`, error);
    throw error;
  }
};

// Función para formatear fecha manteniendo la hora local seleccionada
const formatLocalDate = (date) => {
  // Obtener los componentes de fecha y hora
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  
  // Formato: YYYY-MM-DDTHH:mm:ss (mantiene la hora local)
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};

// Crear un nuevo acto litúrgico
export const createLiturgicalEvent = async (eventData) => {
  try {
    // Convertir estructura del frontend a la esperada por el backend
    // Usar la función personalizada para mantener la hora local
    const backendData = {
      fechaHora: formatLocalDate(new Date(eventData.startDateTime)),
      tipo: { id: eventData.liturgicalActTypeId },
      iglesia: { id: eventData.churchId }
    };

    return await httpClient('/actos-liturgicos', {
      method: "POST",
      body: JSON.stringify(backendData),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (error) {
    console.error('Error al crear acto litúrgico:', error);
    throw error;
  }
};

// Actualizar un acto litúrgico existente
export const updateLiturgicalEvent = async (id, eventData) => {
  try {
    // Convertir estructura del frontend a la esperada por el backend
    // Usar la función personalizada para mantener la hora local
    const backendData = {
      fechaHora: formatLocalDate(new Date(eventData.startDateTime)),
      tipo: { id: eventData.liturgicalActTypeId },
      iglesia: { id: eventData.churchId }
    };
    
    return await httpClient(`/actos-liturgicos/${id}`, {
      method: "PUT",
      body: JSON.stringify(backendData),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (error) {
    console.error(`Error al actualizar acto litúrgico con ID ${id}:`, error);
    throw error;
  }
};

// Eliminar un acto litúrgico
export const deleteLiturgicalEvent = async (id) => {
  try {
    return await httpClient(`/actos-liturgicos/${id}`, {
      method: "DELETE",
    });
  } catch (error) {
    console.error(`Error al eliminar acto litúrgico con ID ${id}:`, error);
    throw error;
  }
};