const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const IMAGE_BASE_URL = process.env.REACT_APP_IMAGE_BASE_URL;

const apiRoutes = {
  churches: {
    getAll: `${API_BASE_URL}/iglesias`,
    getById: (id) => `${API_BASE_URL}/iglesias/${id}`,
    create: `${API_BASE_URL}/iglesias`,
    update: (id) => `${API_BASE_URL}/iglesias/${id}`,
    delete: (id) => `${API_BASE_URL}/iglesias/${id}`,
  },
  images: {
    base: IMAGE_BASE_URL,
  },
};

export default apiRoutes;