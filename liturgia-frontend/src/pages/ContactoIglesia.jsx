// src/pages/ContactoIglesia.jsx
import { useEffect, useState } from "react";
import apiRoutes from "../config/apiConfig";
import Navbar from "../components/Navbar";

const ContactoIglesia = () => {
  const [iglesias, setIglesias] = useState([]);
  const [iglesiaSeleccionada, setIglesiaSeleccionada] = useState(null);
  const [form, setForm] = useState({ nombre: "", correo: "", telefono: "", mensaje: "" });

  useEffect(() => {
    fetch(apiRoutes.churches.getAll)
      .then((res) => res.json())
      .then((data) => setIglesias(data))
      .catch((err) => console.error("Error al cargar iglesias:", err));
  }, []);

  const handleSeleccion = (e) => {
    const id = e.target.value;
    const iglesia = iglesias.find((ig) => ig.id === parseInt(id));
    setIglesiaSeleccionada(iglesia);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Formulario enviado:", form);
  };

  return (
    <div className="bg-[#F9FBFC] text-[#2C3E50] min-h-screen">
      <Navbar />

      {/* Hero Section */}
      <div className="relative bg-[#2C3E50] text-white text-center py-20 px-4">
        <h1 className="text-4xl font-bold mb-4">Contáctanos</h1>
        <p className="text-lg max-w-xl mx-auto">Estamos listos para ayudarte y brindarte la mejor experiencia espiritual y cultural con nuestras iglesias.</p>
      </div>

      {/* Form & Info Section */}
      <div className="max-w-6xl mx-auto grid md:grid-cols-2 gap-10 bg-white p-10 mt-[-4rem] rounded-xl shadow-lg">
        {/* Info izquierda */}
        <div className="space-y-6">
          <h2 className="text-2xl font-bold">Información de contacto</h2>
          {iglesiaSeleccionada && (
            <div className="text-sm text-gray-700 space-y-2">
              <p><strong>Nombre:</strong> {iglesiaSeleccionada.nombre}</p>
              <p><strong>Dirección:</strong> {iglesiaSeleccionada.direccion || "No registrada"}</p>
              <p><strong>Teléfono:</strong> {iglesiaSeleccionada.telefono || "No registrado"}</p>
              <p><strong>Horario:</strong> {iglesiaSeleccionada.horario || "No disponible"}</p>
            </div>
          )}
          <div className="space-y-3">
            <label className="block font-semibold">Selecciona una iglesia:</label>
            <select
              onChange={handleSeleccion}
              className="w-full border border-gray-300 rounded-md p-2"
              defaultValue=""
            >
              <option disabled value="">-- Selecciona una iglesia --</option>
              {iglesias.map((iglesia) => (
                <option key={iglesia.id} value={iglesia.id}>{iglesia.nombre}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Formulario */}
        <form onSubmit={handleSubmit} className="space-y-4">
          <h2 className="text-2xl font-bold mb-4">Escríbenos un mensaje</h2>
          <input
            type="text"
            name="nombre"
            placeholder="Tu nombre"
            onChange={handleChange}
            className="w-full p-2 border border-gray-300 rounded-md"
            required
          />
          <input
            type="email"
            name="correo"
            placeholder="Tu correo"
            onChange={handleChange}
            className="w-full p-2 border border-gray-300 rounded-md"
            required
          />
          <input
            type="text"
            name="telefono"
            placeholder="Tu teléfono"
            onChange={handleChange}
            className="w-full p-2 border border-gray-300 rounded-md"
          />
          <textarea
            name="mensaje"
            placeholder="Tu mensaje"
            rows="5"
            onChange={handleChange}
            className="w-full p-2 border border-gray-300 rounded-md"
            required
          ></textarea>
          <button className="bg-[#F5CBA7] hover:bg-[#F0B27A] text-white px-6 py-2 rounded-md shadow">
            Enviar
          </button>
        </form>
      </div>

      {/* Mapa */}
      <div className="mt-10">
        <iframe
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3901.870088938513!2d-71.96746208527692!3d-13.51502489049309!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x911f1a6f0b95b6e7%3A0x9f2f3b46b3df3f1a!2sCusco%2C%20Per%C3%BA!5e0!3m2!1ses!2spe!4v1681513769290!5m2!1ses!2spe"
          width="100%"
          height="350"
          style={{ border: 0 }}
          allowFullScreen=""
          loading="lazy"
          referrerPolicy="no-referrer-when-downgrade"
        ></iframe>
      </div>
    </div>
  );
};

export default ContactoIglesia;
