import { useState } from "react";
import { FaChevronDown, FaChevronUp, FaStar, FaCalendarAlt } from "react-icons/fa";
import { DateRange } from "react-date-range";
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";

const Sidebar = ({ filtros, setFiltros }) => {
  const [showRatings, setShowRatings] = useState(false);
  const [showBuenoPara, setShowBuenoPara] = useState(false);
  const [showCalendar, setShowCalendar] = useState(false);

  const [range, setRange] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    },
  ]);

  const handleCheckbox = (e) => {
    const { name, checked } = e.target;
    setFiltros((prev) => ({ ...prev, [name]: checked }));
  };

  return (
    <aside className="w-full md:w-72 bg-[#1a2b24] p-6 rounded-xl shadow-md text-white font-sans">
      {/* Botón Ingresar Fechas */}
      <div className="mb-6 relative">
        <button
          onClick={() => setShowCalendar(!showCalendar)}
          className="w-full flex items-center justify-center gap-2 border border-[#64B374] text-[#E3E5D8] font-medium py-2 px-4 rounded-full hover:bg-[#355245] hover:text-white transition"
        >
          <FaCalendarAlt className="text-[#64B374]" />
          Ingresar las fechas
        </button>

        {showCalendar && (
          <div className="absolute z-50 mt-2 bg-white rounded shadow-lg text-black">
            <DateRange
              editableDateInputs={true}
              onChange={(item) => setRange([item.selection])}
              moveRangeOnFirstSelection={false}
              ranges={range}
              months={2}
              direction="vertical"
              className="text-sm"
            />
          </div>
        )}
      </div>

      <h2 className="text-2xl font-bold mb-6 text-[#AFC1B6] text-center">
        Filtros de búsqueda
      </h2>

      <div className="space-y-4">
        <label className="flex items-center gap-2">
          <input
            type="checkbox"
            name="conDescripcion"
            checked={filtros.conDescripcion}
            onChange={handleCheckbox}
            className="form-checkbox accent-[#64B374]"
          />
          Solo con descripción
        </label>
        <label className="flex items-center gap-2">
          <input
            type="checkbox"
            name="conImagen"
            checked={filtros.conImagen}
            onChange={handleCheckbox}
            className="form-checkbox accent-[#64B374]"
          />
          Solo con imagen
        </label>
        <label className="flex items-center gap-2">
          <input
            type="checkbox"
            name="ordenAlfabetico"
            checked={filtros.ordenAlfabetico}
            onChange={handleCheckbox}
            className="form-checkbox accent-[#64B374]"
          />
          Ordenar alfabéticamente
        </label>
      </div>

      {/* Calificación de Iglesias */}
      <div className="mt-8">
        <div className="flex justify-between items-center mb-2">
          <p className="font-semibold text-[#AFC1B6]">Calificación de Iglesias</p>
          <button
            onClick={() => setShowRatings(!showRatings)}
            className="text-[#64B374] text-sm flex items-center gap-1"
          >
            {showRatings ? "Mostrar menos" : "Mostrar más"}
            {showRatings ? <FaChevronUp /> : <FaChevronDown />}
          </button>
        </div>
        {showRatings && (
          <div className="space-y-1 mt-2 text-yellow-400">
            {[5, 4, 3, 2, 1].map((stars) => (
              <div key={stars} className="flex items-center gap-1">
                {Array.from({ length: stars }).map((_, i) => (
                  <FaStar key={i} />
                ))}
                <span className="text-sm text-[#E3E5D8] ml-2">y más</span>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Bueno para */}
      <div className="mt-8">
        <div className="flex justify-between items-center mb-2">
          <p className="font-semibold text-[#AFC1B6]">Bueno para</p>
          <button
            onClick={() => setShowBuenoPara(!showBuenoPara)}
            className="text-[#64B374] text-sm flex items-center gap-1"
          >
            {showBuenoPara ? "Mostrar menos" : "Mostrar más"}
            {showBuenoPara ? <FaChevronUp /> : <FaChevronDown />}
          </button>
        </div>
        {showBuenoPara && (
          <div className="space-y-2 mt-2 text-[#E3E5D8] text-sm">
            {["Viajeros", "Turistas", "Ciudadanos locales", "Adultos mayores"].map((item) => (
              <label key={item} className="flex items-center gap-2">
                <input type="checkbox" className="form-checkbox accent-[#64B374]" />
                {item}
              </label>
            ))}
          </div>
        )}
      </div>
    </aside>
  );
};

export default Sidebar;


  
