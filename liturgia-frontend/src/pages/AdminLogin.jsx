import React, { useState } from "react";
import { login } from "../dashboard/usuarios/services/authService";
import { useNavigate } from "react-router-dom";
import { useUser } from "../context/UserContext";

export default function AdminLogin() {
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { loadUserFromToken } = useUser();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await login(formData);
      localStorage.setItem("token", data.token);
      loadUserFromToken();
      alert("Inicio de sesión exitoso");
      navigate("/dashboard");
    } catch (err) {
      console.error(err);
      setError("Credenciales inválidas");
    }
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center bg-cover bg-center"
      style={{
        backgroundImage:
          "url('https://raw.githubusercontent.com/DanniHMG/FloresAmarillas/refs/heads/main/photo-1691215838956-0345bd1eebb6.avif')",
      }}
    >
      <div className="bg-white bg-opacity-80 shadow-xl rounded-2xl flex p-10 w-[900px] max-w-6xl">
        <div className="w-1/2 flex flex-col justify-center px-6">
          <h2 className="text-3xl font-bold text-gray-800 mb-2">Good Morning</h2>
          <p className="text-sm text-gray-600">
            Have a great journey ahead!
          </p>
        </div>

        <div className="w-1/2 bg-slate-900 bg-opacity-90 rounded-xl p-8 text-white">
          <h2 className="text-lg font-semibold mb-4">USERNAME</h2>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            className="w-full mb-4 px-4 py-2 rounded-md text-black"
            placeholder="Usuario"
            required
          />

          <h2 className="text-lg font-semibold mb-4">PASSWORD</h2>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className="w-full mb-4 px-4 py-2 rounded-md text-black"
            placeholder="Contraseña"
            required
          />

          <div className="flex items-center mb-4">
            <input type="checkbox" id="remember" className="mr-2" />
            <label htmlFor="remember" className="text-sm">
              Remember me
            </label>
          </div>

          {error && <p className="text-red-400 text-sm mb-2">{error}</p>}

          <button
            onClick={handleSubmit}
            className="w-full bg-cyan-400 text-white py-2 rounded-md font-bold hover:bg-cyan-500 transition"
          >
            SIGN IN
          </button>

          <div className="mt-4 text-sm">
            Don’t have an account?{' '}
            <span className="text-blue-300 hover:underline cursor-pointer">
              Sign Up
            </span>
          </div>
          <div className="text-xs mt-2 text-gray-300">Forgot Password?</div>
        </div>
      </div>
    </div>
  );
}