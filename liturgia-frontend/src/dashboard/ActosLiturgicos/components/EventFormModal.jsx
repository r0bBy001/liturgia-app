import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import DatePicker, { registerLocale } from 'react-datepicker';
import { es } from 'date-fns/locale';
import "react-datepicker/dist/react-datepicker.css";
import { createLiturgicalEvent, updateLiturgicalEvent } from '../services/liturgicalEventService';
import { getAllLiturgicalActs } from '../../tipoActos/services/liturgicalActService';
import { getAllChurches } from '../../iglesias/services/churchService';

// Registrar locale español para el DatePicker
registerLocale('es', es);

// Estilos personalizados para sobrescribir cualquier problema de color
const customStyles = {
  input: "w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 text-gray-900",
  select: "w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 text-gray-900",
  datePicker: "w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 text-gray-900"
};

const EventFormModal = ({ event, isEditMode, onClose, onEventAdded, churchId }) => {
  const [startDateTime, setStartDateTime] = useState(new Date());
  const [selectedChurchId, setSelectedChurchId] = useState('');
  const [selectedTypeId, setSelectedTypeId] = useState('');
  const [churches, setChurches] = useState([]);
  const [liturgicalActTypes, setLiturgicalActTypes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [formError, setFormError] = useState('');

  // Cargar datos iniciales
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Cargar tipos de actos litúrgicos
        const typesData = await getAllLiturgicalActs();
        setLiturgicalActTypes(typesData);

        // Si no es un encargado, cargar todas las iglesias
        if (!churchId) {
          const churchesData = await getAllChurches();
          setChurches(churchesData);
        }

        // Si estamos en modo edición, rellenar el formulario con los datos del evento
        if (isEditMode && event) {
          setStartDateTime(event.startDateTime ? new Date(event.startDateTime) : new Date());
          setSelectedChurchId(event.churchId || '');
          setSelectedTypeId(event.liturgicalActTypeId || '');
        } else if (event) {
          // Si es un nuevo evento pero tenemos información previa
          setStartDateTime(event.startDateTime ? new Date(event.startDateTime) : new Date());
          setSelectedChurchId(event.churchId || '');
        } else {
          // Si es un nuevo evento sin información previa
          setSelectedChurchId(churchId || '');
        }
      } catch (error) {
        console.error('Error al cargar datos iniciales:', error);
        setFormError('Error al cargar datos iniciales. Intente nuevamente.');
      }
    };

    fetchData();
  }, [event, isEditMode, churchId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validación básica
    if (!selectedTypeId) {
      setFormError('Debe seleccionar un tipo de acto litúrgico');
      return;
    }

    if (!selectedChurchId && !churchId) {
      setFormError('Debe seleccionar una iglesia');
      return;
    }

    // Preparar datos para envío según el modelo del backend
    const eventData = {
      startDateTime: startDateTime.toISOString(),
      liturgicalActTypeId: selectedTypeId,
      churchId: selectedChurchId || churchId
    };

    setLoading(true);
    setFormError('');

    try {
      if (isEditMode && event?.id) {
        await updateLiturgicalEvent(event.id, eventData);
      } else {
        await createLiturgicalEvent(eventData);
      }
      
      onEventAdded();
    } catch (error) {
      console.error('Error al guardar el evento:', error);
      setFormError('Error al guardar el evento. Intente nuevamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 backdrop-blur-sm">
      <motion.div
        initial={{ scale: 0.9, opacity: 0, y: 20 }}
        animate={{ scale: 1, opacity: 1, y: 0 }}
        exit={{ scale: 0.9, opacity: 0, y: 20 }}
        transition={{ type: 'spring', stiffness: 300, damping: 30 }}
        className="bg-white rounded-xl shadow-2xl w-full max-w-lg max-h-[90vh] overflow-hidden"
      >
        <div className="bg-gradient-to-r from-indigo-600 to-purple-600 p-6 text-white">
          <h2 className="text-2xl font-bold">
            {isEditMode ? 'Editar Acto Litúrgico' : 'Crear Nuevo Acto Litúrgico'}
          </h2>
          <p className="text-indigo-100 mt-2">
            Complete los detalles para {isEditMode ? 'actualizar el' : 'crear un nuevo'} acto litúrgico
          </p>
        </div>

        <div className="p-6 overflow-y-auto max-h-[calc(90vh-180px)]">
          {formError && (
            <div className="mb-4 bg-red-50 text-red-700 p-3 rounded-md border border-red-200">
              <div className="flex items-center">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                </svg>
                <span>{formError}</span>
              </div>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            {/* Tipo de acto litúrgico */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Tipo de Acto Litúrgico <span className="text-red-500">*</span>
              </label>
              <select
                value={selectedTypeId}
                onChange={(e) => setSelectedTypeId(e.target.value)}
                className={customStyles.select}
                required
              >
                <option value="">Seleccione un tipo</option>
                {liturgicalActTypes.map((type) => (
                  <option key={type.id} value={type.id} className="text-gray-900">
                    {type.nombre}
                  </option>
                ))}
              </select>
            </div>

            {/* Iglesia (solo si no es encargado) */}
            {!churchId && (
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Iglesia <span className="text-red-500">*</span>
                </label>
                <select
                  value={selectedChurchId}
                  onChange={(e) => setSelectedChurchId(e.target.value)}
                  className={customStyles.select}
                  required
                >
                  <option value="">Seleccione una iglesia</option>
                  {churches.map((church) => (
                    <option key={church.id} value={church.id} className="text-gray-900">
                      {church.nombre}
                    </option>
                  ))}
                </select>
              </div>
            )}

            {/* Fecha y hora */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Fecha y hora <span className="text-red-500">*</span>
              </label>
              <DatePicker
                selected={startDateTime}
                onChange={date => setStartDateTime(date)}
                showTimeSelect
                timeFormat="HH:mm"
                timeIntervals={15}
                dateFormat="dd/MM/yyyy HH:mm"
                locale="es"
                className={customStyles.datePicker}
                required
              />
            </div>
          </form>
        </div>

        <div className="border-t border-gray-200 p-4 bg-gray-50 flex justify-end space-x-3">
          <button
            onClick={onClose}
            className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300 transition-colors focus:outline-none focus:ring-2 focus:ring-gray-400"
          >
            Cancelar
          </button>
          <button
            onClick={handleSubmit}
            disabled={loading}
            className={`px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors flex items-center focus:outline-none focus:ring-2 focus:ring-indigo-500 ${
              loading ? 'opacity-75 cursor-not-allowed' : ''
            }`}
          >
            {loading && (
              <svg className="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
            )}
            {isEditMode ? 'Actualizar' : 'Crear Evento'}
          </button>
        </div>
      </motion.div>
    </div>
  );
};

export default EventFormModal;