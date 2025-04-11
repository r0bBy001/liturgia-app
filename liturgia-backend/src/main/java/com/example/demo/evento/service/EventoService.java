package com.example.demo.evento.service;

import com.example.demo.evento.model.Evento;
import com.example.demo.evento.repository.EventoRepository;
import com.example.demo.imagen.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ImageService imageService;

    public EventoService(EventoRepository eventoRepository, ImageService imageService) {
        this.eventoRepository = eventoRepository;
        this.imageService = imageService;
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento guardarEvento(Evento evento, MultipartFile imagen) throws IOException {
        if (imagen != null && !imagen.isEmpty()) {
            String urlImagen = imageService.saveImage(imagen, "eventos");
            evento.setImagen(urlImagen);
        }
        return eventoRepository.save(evento);
    }

    public Evento obtenerEventoPorId(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento actualizarEvento(Long id, Evento evento, MultipartFile nuevaImagen) throws IOException {
        return eventoRepository.findById(id).map(eventoExistente -> {
            eventoExistente.setTitulo(evento.getTitulo());
            eventoExistente.setDescripcion(evento.getDescripcion());
            eventoExistente.setFechaInicio(evento.getFechaInicio());
            eventoExistente.setFechaFin(evento.getFechaFin());
            eventoExistente.setDestacado(evento.isDestacado());
            eventoExistente.setIglesia(evento.getIglesia());
            try {
                if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
                    String urlImagen = imageService.saveImage(nuevaImagen, "eventos");
                    eventoExistente.setImagen(urlImagen);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen", e);
            }
            return eventoRepository.save(eventoExistente);
        }).orElse(null);
    }

    public void eliminarEvento(Long id) {
        eventoRepository.deleteById(id);
    }
}
