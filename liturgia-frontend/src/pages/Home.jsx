import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaMapMarkerAlt, FaChurch, FaInfoCircle } from "react-icons/fa";
import apiRoutes from "../config/apiConfig";
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import Navbar from "../components/Navbar";

export default function Home() {
  const [form, setForm] = useState({ nombre: "", correo: "", telefono: "" });
  const [iglesias, setIglesias] = useState([]);
  const [bannerIndex, setBannerIndex] = useState(0);
  const [iglesiaSeleccionada, setIglesiaSeleccionada] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Formulario enviado:", form);
  };

  useEffect(() => {
    fetch(apiRoutes.churches.getAll)
      .then((res) => res.json())
      .then((data) => setIglesias(data))
      .catch((error) => console.error("Error al obtener iglesias:", error));
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setBannerIndex((prev) => (prev + 1) % iglesias.length);
    }, 4000);
    return () => clearInterval(interval);
  }, [iglesias]);

  return (
    <div className="bg-[#EAF2F8] text-[#2C3E50]">
      <Navbar />

      {/* HERO CON FORMULARIO */}
      <div className="relative">
        <img
          src={
            iglesias.length > 0 && iglesias[bannerIndex]?.portada
              ? `${apiRoutes.images.base}/iglesia/portadas/${iglesias[bannerIndex].portada}`
              : "https://source.unsplash.com/1600x450/?church,cusco"
          }
          alt="Banner principal"
          className="w-full h-[450px] object-cover brightness-75 transition-all duration-700"
        />
        <div className="absolute top-14 right-10 bg-white text-[#2C3E50] p-6 rounded-xl shadow-xl w-[340px]">
          <h2 className="text-lg font-semibold mb-3">¿Deseas que te contactemos?</h2>
          <form className="space-y-3" onSubmit={handleSubmit}>
            <input name="nombre" placeholder="Tu nombre" value={form.nombre} onChange={handleChange} className="w-full p-2 bg-[#F8F9F9] border border-[#2C3E50] rounded" />
            <input name="correo" placeholder="Tu correo" value={form.correo} onChange={handleChange} className="w-full p-2 bg-[#F8F9F9] border border-[#2C3E50] rounded" />
            <input name="telefono" placeholder="Tu teléfono" value={form.telefono} onChange={handleChange} className="w-full p-2 bg-[#F8F9F9] border border-[#2C3E50] rounded" />
            <label className="text-sm"><input type="checkbox" required className="mr-2 accent-[#F5CBA7]" />Acepto ser contactado por este medio.</label>
            <button className="w-full bg-[#F5CBA7] hover:bg-[#F0B27A] text-white py-2 rounded">Enviar</button>
          </form>
        </div>
      </div>

      {/* INTRODUCCIÓN */}
      <section className="max-w-7xl mx-auto px-6 py-14 text-center">
        <h2 className="text-3xl md:text-4xl font-bold text-[#E8CDA3]">Descubre la fe, la historia y la arquitectura</h2>
        <p className="text-gray-700 mt-3 text-lg">Explora las iglesias más emblemáticas de Cusco. Un recorrido espiritual y cultural que te conecta con la esencia de una ciudad milenaria.</p>
      </section>

      {/* IGLESIAS DESTACADAS */}
      <section className="max-w-7xl mx-auto px-6 pb-20">
        <h2 className="text-2xl font-bold text-center mb-8">Iglesias destacadas</h2>
        <Swiper
          spaceBetween={20}
          slidesPerView={1}
          breakpoints={{ 768: { slidesPerView: 2 }, 1024: { slidesPerView: 3 } }}
          navigation
          modules={[Navigation]}
        >
          {iglesias.map((iglesia) => (
            <SwiperSlide key={iglesia.id}>
              <div onClick={() => setIglesiaSeleccionada(iglesia)} className="group bg-white rounded-xl shadow-md hover:shadow-2xl transition-all duration-300 overflow-hidden cursor-pointer">
                <img src={iglesia.portada ? `${apiRoutes.images.base}/iglesia/portadas/${iglesia.portada}` : "https://via.placeholder.com/400x300?text=Sin+imagen"} alt={iglesia.nombre} className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-500" />
                <div className="p-4">
                  <h3 className="text-xl font-semibold flex items-center gap-2 mb-2"><FaChurch className="text-[#F5CBA7]" />{iglesia.nombre}</h3>
                  <p className="text-sm text-gray-600">{iglesia.descripcion || 'Descripción no disponible.'}</p>
                </div>
              </div>
            </SwiperSlide>
          ))}
        </Swiper>

        {/* DETALLE IGLESIA */}
        {iglesiaSeleccionada && (
          <div className="mt-12 flex flex-col md:flex-row gap-6 bg-white rounded-xl shadow-lg p-6">
            <img src={`${apiRoutes.images.base}/iglesia/portadas/${iglesiaSeleccionada.portada}`} alt={iglesiaSeleccionada.nombre} className="w-full md:w-1/2 h-64 object-cover rounded-lg" />
            <div className="flex flex-col justify-between">
              <div>
                <h3 className="text-2xl font-bold mb-2 flex items-center gap-2"><FaInfoCircle className="text-[#F5CBA7]" />{iglesiaSeleccionada.nombre}</h3>
                <p className="text-gray-700 mb-3">{iglesiaSeleccionada.descripcion || "No hay descripción disponible para esta iglesia."}</p>
                <p className="flex items-center text-sm text-gray-500"><FaMapMarkerAlt className="mr-1 text-[#64B374]" />Cusco, Perú</p>
              </div>
              <button onClick={() => navigate(`/iglesias/${iglesiaSeleccionada.id}`)} className="mt-4 bg-[#F5CBA7] hover:bg-[#F0B27A] text-white py-2 px-4 rounded-md w-fit">Ver perfil completo</button>
            </div>
          </div>
        )}
      </section>

      {/* BLOQUE INSPIRADOR ABAJO */}
      <section className="bg-white py-16 mt-8">
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-3 gap-12 text-center">
          <div>
            <img src="https://cdn-icons-png.flaticon.com/512/2302/2302717.png" className="mx-auto w-12 h-12" />
            <h4 className="mt-4 font-bold text-[#F5CBA7]">Tradición Viva</h4>
            <p className="text-sm text-gray-600">Sumérgete en las tradiciones milenarias que aún viven en cada iglesia del Cusco.</p>
          </div>
          <div>
            <img src="https://cdn-icons-png.flaticon.com/512/535/535239.png" className="mx-auto w-12 h-12" />
            <h4 className="mt-4 font-bold text-[#F5CBA7]">Belleza Arquitectónica</h4>
            <p className="text-sm text-gray-600">Admira los detalles barrocos y coloniales que embellecen cada templo cusqueño.</p>
          </div>
          <div>
            <img src="https://cdn-icons-png.flaticon.com/512/3469/3469331.png" className="mx-auto w-12 h-12" />
            <h4 className="mt-4 font-bold text-[#F5CBA7]">Espiritualidad</h4>
            <p className="text-sm text-gray-600">Un espacio para la reflexión, la fe y la conexión con lo trascendental.</p>
          </div>
        </div>
        <div className="text-center mt-12">
          <h3 className="text-xl font-semibold mb-2 text-[#2C3E50]">¿Estás listo para comenzar tu recorrido?</h3>
          <p className="text-gray-600 mb-4">Explora con nosotros los tesoros espirituales y arquitectónicos del Cusco. Da el primer paso hacia una experiencia única y enriquecedora.</p>
          <button className="bg-[#64B374] hover:bg-[#4fa660] text-white px-6 py-2 rounded-md shadow">Empezar ahora</button>
        </div>
      </section>

      {/* NUEVA SECCIÓN TIPO LANDING INSPIRACIONAL */}
      <section className="bg-gradient-to-r from-blue-600 to-indigo-600 text-white py-16">
        <div className="max-w-6xl mx-auto px-4 text-center">
          <h2 className="text-3xl font-bold mb-8">Descubre nuestras experiencias</h2>
          <div className="grid md:grid-cols-4 gap-6">
            <div className="bg-white/10 p-4 rounded-lg backdrop-blur">
              <h3 className="text-xl font-semibold mb-2">Visitas Guiadas</h3>
              <p className="text-sm">Explora con guía profesional cada rincón espiritual y arquitectónico.</p>
            </div>
            <div className="bg-white/10 p-4 rounded-lg backdrop-blur">
              <h3 className="text-xl font-semibold mb-2">Historia Viva</h3>
              <p className="text-sm">Conoce las leyendas y hechos históricos que rodean cada iglesia.</p>
            </div>
            <div className="bg-white/10 p-4 rounded-lg backdrop-blur">
              <h3 className="text-xl font-semibold mb-2">Eventos Especiales</h3>
              <p className="text-sm">Participa en celebraciones religiosas y culturales únicas.</p>
            </div>
            <div className="bg-white/10 p-4 rounded-lg backdrop-blur">
              <h3 className="text-xl font-semibold mb-2">Fotografía y Arte</h3>
              <p className="text-sm">Captura la belleza artística de cada estructura sagrada.</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}