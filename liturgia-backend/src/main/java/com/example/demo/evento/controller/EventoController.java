package com.example.demo.evento.controller;

import com.example.demo.evento.model.Evento;
import com.example.demo.evento.service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Evento> guardarEvento(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("destacado") boolean destacado,
            @RequestParam("iglesiaId") Long iglesiaId,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            Evento evento = new Evento();
            evento.setTitulo(titulo);
            evento.setDescripcion(descripcion);
            evento.setFechaInicio(java.time.LocalDate.parse(fechaInicio));
            evento.setFechaFin(java.time.LocalDate.parse(fechaFin));
            evento.setDestacado(destacado);
            evento.setIglesia(new com.example.demo.Iglesia.model.Iglesia());
            evento.getIglesia().setId(iglesiaId);

            Evento nuevoEvento = eventoService.guardarEvento(evento, imagen);
            return ResponseEntity.ok(nuevoEvento);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerEventoPorId(@PathVariable Long id) {
        Evento evento = eventoService.obtenerEventoPorId(id);
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evento);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Evento> actualizarEvento(
            @PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("destacado") boolean destacado,
            @RequestParam("iglesiaId") Long iglesiaId,
            @RequestPart(value = "nuevaImagen", required = false) MultipartFile nuevaImagen) {
        try {
            Evento evento = new Evento();
            evento.setTitulo(titulo);
            evento.setDescripcion(descripcion);
            evento.setFechaInicio(java.time.LocalDate.parse(fechaInicio));
            evento.setFechaFin(java.time.LocalDate.parse(fechaFin));
            evento.setDestacado(destacado);
            evento.setIglesia(new com.example.demo.Iglesia.model.Iglesia());
            evento.getIglesia().setId(iglesiaId);

            Evento eventoActualizado = eventoService.actualizarEvento(id, evento, nuevaImagen);
            if (eventoActualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(eventoActualizado);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }
}
