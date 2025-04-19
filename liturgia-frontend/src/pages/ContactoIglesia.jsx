// src/pages/ContactoIglesia.jsx
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import apiRoutes from "../config/apiConfig";

const ContactoIglesia = () => {
  const { id } = useParams();
  const [iglesia, setIglesia] = useState(null);

  useEffect(() => {
    fetch(apiRoutes.churches.getById(id))
      .then(res => res.json())
      .then(data => setIglesia(data))
      .catch(err => console.error("Error al cargar datos:", err));
  }, [id]);

  if (!iglesia) {
    return <div className="text-center mt-10 text-gray-500">Cargando información...</div>;
  }

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white mt-10 rounded-xl shadow-lg">
      <h1 className="text-3xl font-bold text-[#2C3E50] mb-4">{iglesia.nombre}</h1>
      <p className="text-gray-700 mb-2">
        <strong>Dirección:</strong> {iglesia.direccion || "Dirección no registrada"}
      </p>
      <p className="text-gray-700 mb-2">
        <strong>Teléfono:</strong> {iglesia.telefono || "Teléfono no registrado"}
      </p>
      <p className="text-gray-700 mb-6">
        <strong>Horario:</strong> {iglesia.horario || "Horario no disponible"}
      </p>

      <form className="space-y-4">
        <h2 className="text-xl font-semibold mb-2 text-[#2C3E50]">Contáctanos</h2>
        <input
          placeholder="Tu nombre"
          className="w-full border border-gray-300 p-2 rounded-md"
        />
        <input
          placeholder="Tu correo"
          type="email"
          className="w-full border border-gray-300 p-2 rounded-md"
        />
        <textarea
          placeholder="Tu mensaje"
          rows="4"
          className="w-full border border-gray-300 p-2 rounded-md"
        ></textarea>
        <button
          type="submit"
          className="bg-[#F5CBA7] hover:bg-[#F0B27A] text-white px-4 py-2 rounded-md"
        >
          Enviar
        </button>
      </form>
    </div>
  );
};

export default ContactoIglesia;
