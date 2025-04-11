import { Link } from "react-router-dom";

function Navbar() {
  return (
    <header className="bg-[#101916] shadow-md">
      <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center text-white">
        <Link to="/" className="text-xl font-bold text-[#64B374]">
          Iglesias del Cusco
        </Link>

        <nav className="space-x-6 text-[#AFC1B6]">
          <Link to="/">Inicio</Link>
          <Link to="/horarios">Horarios</Link>
          <Link to="/mapa">Mapa</Link>
          <Link to="/foros">Foros</Link>
        </nav>

        <button className="bg-[#64B374] hover:bg-[#4fa660] text-white px-4 py-1.5 rounded transition">
          Iniciar sesión
        </button>
      </div>
    </header>
  );
}

export default Navbar;

