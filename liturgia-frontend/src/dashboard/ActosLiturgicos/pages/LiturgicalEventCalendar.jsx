import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import listPlugin from '@fullcalendar/list';
import { useUser } from '../../../context/UserContext';
import { getAllLiturgicalEvents, deleteLiturgicalEvent } from '../services/liturgicalEventService';
import EventDetailModal from '../components/EventDetailModal';
import EventFormModal from '../components/EventFormModal';
import '../../../assets/styles/calendar.css';

const LiturgicalEventCalendar = () => {
  const { user } = useUser();
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [isFormModalOpen, setIsFormModalOpen] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [loading, setLoading] = useState(true);
  const [view, setView] = useState('dayGridMonth');

  const isEncargado = user?.role === 'ENCARGADO';
  const churchId = isEncargado ? user?.iglesiaId : null;

  useEffect(() => {
    fetchEvents();
  }, [churchId]);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const data = await getAllLiturgicalEvents(churchId);
      
      // Convertir los datos del backend al formato esperado por FullCalendar
      const formattedEvents = data.map(event => ({
        id: event.id,
        title: event.tipo?.nombre || 'Evento sin título',
        start: new Date(event.fechaHora),
        // Para endDate, usamos la fecha de inicio + 1 hora como estimación
        end: new Date(new Date(event.fechaHora).getTime() + 60 * 60 * 1000),
        extendedProps: {
          tipo: event.tipo,
          iglesia: event.iglesia
        },
        backgroundColor: getEventColor(event.tipo?.nombre),
        borderColor: getEventColor(event.tipo?.nombre),
        textColor: 'white', // Garantizar texto blanco en todas las vistas
      }));
      
      setEvents(formattedEvents);
    } catch (error) {
      console.error('Error al cargar eventos:', error);
    } finally {
      setLoading(false);
    }
  };

  // Función para asignar colores según el tipo de acto litúrgico
  const getEventColor = (typeName) => {
    const colors = {
      'Misa': '#4CAF50',
      'Bautizo': '#2196F3',
      'Matrimonio': '#F44336',
      'Confesión': '#9C27B0',
      'Comunión': '#FF9800',
      'Confirmación': '#3F51B5',
      'Vigilia': '#795548',
      'Retiro': '#607D8B',
    };
    
    return colors[typeName] || '#009688'; // Color predeterminado
  };

  const handleDateClick = (arg) => {
    setSelectedEvent({
      startDateTime: arg.date,
      liturgicalActTypeId: '',
      churchId: isEncargado ? user.iglesiaId : ''
    });
    setIsEditMode(false);
    setIsFormModalOpen(true);
  };

  const handleEventClick = (info) => {
    const { event } = info;
    setSelectedEvent({
      id: event.id,
      title: event.title,
      startDateTime: event.start,
      endDateTime: event.end,
      liturgicalActType: event.extendedProps.tipo,
      liturgicalActTypeId: event.extendedProps.tipo?.id,
      iglesia: event.extendedProps.iglesia,
      churchId: event.extendedProps.iglesia?.id
    });
    setIsDetailModalOpen(true);
  };

  const handleEditEvent = () => {
    setIsDetailModalOpen(false);
    setIsEditMode(true);
    setIsFormModalOpen(true);
  };

  const handleDeleteEvent = async () => {
    if (window.confirm('¿Estás seguro que deseas eliminar este evento?')) {
      try {
        await deleteLiturgicalEvent(selectedEvent.id);
        await fetchEvents();
        setIsDetailModalOpen(false);
      } catch (error) {
        console.error('Error al eliminar el evento:', error);
      }
    }
  };

  const handleEventAdded = () => {
    fetchEvents();
    setIsFormModalOpen(false);
  };

  const handleViewChange = (viewInfo) => {
    setView(viewInfo.view.type);
  };

  return (
    <motion.div 
      initial={{ opacity: 0 }} 
      animate={{ opacity: 1 }} 
      transition={{ duration: 0.5 }}
      className="bg-white rounded-lg shadow-lg p-6"
    >
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-800 mb-2">Calendario de Actos Litúrgicos</h1>
        <p className="text-gray-600">
          {isEncargado ? 'Gestiona los actos litúrgicos de tu iglesia' : 'Gestiona todos los actos litúrgicos'}
        </p>
      </div>

      <div className="flex justify-end mb-4">
        <button
          onClick={() => handleDateClick({ date: new Date() })}
          className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-md transition-all duration-200 flex items-center"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" viewBox="0 0 20 20" fill="currentColor">
            <path fillRule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clipRule="evenodd" />
          </svg>
          Nuevo Evento
        </button>
      </div>

      <motion.div 
        initial={{ y: 20 }} 
        animate={{ y: 0 }} 
        transition={{ duration: 0.3 }}
        className="calendar-container"
      >
        {loading ? (
          <div className="flex justify-center items-center h-96">
            <div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <FullCalendar
            plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin, listPlugin]}
            initialView={view}
            headerToolbar={{
              left: 'prev,next today',
              center: 'title',
              right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
            }}
            views={{
              dayGridMonth: { buttonText: 'Mes' },
              timeGridWeek: { buttonText: 'Semana' },
              timeGridDay: { buttonText: 'Día' },
              listWeek: { buttonText: 'Lista' }
            }}
            events={events}
            locale="es"
            firstDay={1} // Lunes como primer día de la semana
            eventTimeFormat={{
              hour: '2-digit',
              minute: '2-digit',
              meridiem: false,
              hour12: false
            }}
            dateClick={handleDateClick}
            eventClick={handleEventClick}
            viewClassNames="transition-opacity duration-300"
            viewDidMount={handleViewChange}
            height="auto"
            aspectRatio={1.8}
            editable={true}
            selectable={true}
            selectMirror={true}
            dayMaxEvents={true}
            weekends={true}
            nowIndicator={true}
            eventContent={renderEventContent}
          />
        )}
      </motion.div>

      {isDetailModalOpen && selectedEvent && (
        <EventDetailModal
          event={selectedEvent}
          onClose={() => setIsDetailModalOpen(false)}
          onEdit={handleEditEvent}
          onDelete={handleDeleteEvent}
        />
      )}

      {isFormModalOpen && (
        <EventFormModal
          event={selectedEvent}
          isEditMode={isEditMode}
          onClose={() => setIsFormModalOpen(false)}
          onEventAdded={handleEventAdded}
          churchId={isEncargado ? user.iglesiaId : null}
        />
      )}
    </motion.div>
  );
};

// Renderizar el contenido de los eventos según la vista actual
function renderEventContent(eventInfo) {
  // Detectar la vista actual
  const view = eventInfo.view.type;
  
  // Diferentes estilos según el tipo de vista
  if (view === 'listWeek') {
    // Para vista lista, texto oscuro sin sombra
    return (
      <div className="w-full h-full overflow-hidden">
        <div className="font-medium text-gray-900">{eventInfo.event.title}</div>
      </div>
    );
  } else if (view === 'dayGridMonth') {
    // Vista mes
    return (
      <div className="w-full h-full p-1 overflow-hidden">
        <div className="font-bold text-sm text-white" style={{ textShadow: '0px 1px 2px rgba(0,0,0,0.4)' }}>{eventInfo.timeText}</div>
        <div className="text-xs font-semibold truncate text-white" style={{ textShadow: '0px 1px 2px rgba(0,0,0,0.4)' }}>{eventInfo.event.title}</div>
      </div>
    );
  } else {
    // Para vistas día y semana
    return (
      <div className="w-full h-full p-1 overflow-hidden">
        <div className="font-bold text-sm text-white" style={{ textShadow: '0px 1px 2px rgba(0,0,0,0.4)' }}>{eventInfo.timeText}</div>
        <div className="text-sm font-medium truncate text-white" style={{ textShadow: '0px 1px 2px rgba(0,0,0,0.4)' }}>{eventInfo.event.title}</div>
      </div>
    );
  }
}

export default LiturgicalEventCalendar;