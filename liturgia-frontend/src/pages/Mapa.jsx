import { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import apiRoutes from "../config/apiConfig";
import Navbar from "../components/Navbar";

// Icono personalizado para marcadores
const customIcon = new L.Icon({
  iconUrl: "https://cdn-icons-png.flaticon.com/512/684/684908.png",
  iconSize: [32, 32],
  iconAnchor: [16, 32],
});

export default function Mapa() {
  const [iglesias, setIglesias] = useState([]);

  useEffect(() => {
    fetch(apiRoutes.churches.getAll)
      .then((res) => res.json())
      .then((data) => setIglesias(data))
      .catch((err) => console.error("Error cargando iglesias:", err));
  }, []);

  return (
    <div className="bg-[#121212] text-white min-h-screen">
      <Navbar />
      <div className="flex h-[calc(100vh-64px)]">
        {/* Panel izquierdo con lista de iglesias */}
        <div className="w-full md:w-[320px] bg-[#1E1E1E] p-4 overflow-y-auto border-r border-[#333]">
          <h2 className="text-xl font-bold mb-4">Iglesias en Cusco</h2>
          <input
            type="text"
            placeholder="Buscar iglesia..."
            className="w-full mb-4 p-2 rounded bg-[#2A2A2A] text-white border border-[#444]"
          />

          <ul className="space-y-4">
            {iglesias.map((iglesia) => (
              <li key={iglesia.id} className="bg-[#2A2A2A] p-3 rounded-md hover:bg-[#333]">
                <p className="font-semibold text-[#F5CBA7]">{iglesia.nombre}</p>
                <p className="text-sm text-gray-400">{iglesia.ciudad}</p>
              </li>
            ))}
          </ul>
        </div>

        {/* Mapa */}
        <div className="flex-1 relative">
          <MapContainer
            center={[-13.532, -71.967]}
            zoom={13}
            scrollWheelZoom={true}
            className="w-full h-full z-0"
          >
            <TileLayer
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              attribution="&copy; OpenStreetMap contributors"
            />

            {iglesias.map((iglesia) => (
              <Marker
                key={iglesia.id}
                position={[iglesia.latitud || -13.532, iglesia.longitud || -71.967]}
                icon={customIcon}
              >
                <Popup>
                  <h3 className="font-bold">{iglesia.nombre}</h3>
                  <p>{iglesia.descripcion || "Sin descripción"}</p>
                </Popup>
              </Marker>
            ))}
          </MapContainer>
        </div>
      </div>
    </div>
  );
}