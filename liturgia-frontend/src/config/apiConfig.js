const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const IMAGE_BASE_URL = import.meta.env.VITE_IMAGE_BASE_URL;

const apiRoutes = {
  churches: {
    getAll: `${API_BASE_URL}/iglesias`,
    getById: (id) => `${API_BASE_URL}/iglesias/${id}`,
    create: `${API_BASE_URL}/iglesias`,
    update: (id) => `${API_BASE_URL}/iglesias/${id}`,
    delete: (id) => `${API_BASE_URL}/iglesias/${id}`,
  },
  users: {
    getAll: `${API_BASE_URL}/usuarios`,
    getById: (id) => `${API_BASE_URL}/usuarios/${id}`,
    create: `${API_BASE_URL}/usuarios`,
    update: (id) => `${API_BASE_URL}/usuarios/${id}`,
    delete: (id) => `${API_BASE_URL}/usuarios/${id}`,
  },
  images: {
    base: IMAGE_BASE_URL,
  },
  parents: {
    getAll: `${API_BASE_URL}/padres`,
    getById: (id) => `${API_BASE_URL}/padres/${id}`,
    create: `${API_BASE_URL}/padres`,
    update: (id) => `${API_BASE_URL}/padres/${id}`,
    delete: (id) => `${API_BASE_URL}/padres/${id}`,
  },
  liturgicalActs: {
    getAll: `${API_BASE_URL}/tipos-actos`,
    getById: (id) => `${API_BASE_URL}/tipos-actos/${id}`,
    create: `${API_BASE_URL}/tipos-actos`,
    update: (id) => `${API_BASE_URL}/tipos-actos/${id}`,
    delete: (id) => `${API_BASE_URL}/tipos-actos/${id}`,
  },
};

export default apiRoutes;