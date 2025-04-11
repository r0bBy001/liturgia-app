package com.example.demo.noticia.controller;

import com.example.demo.noticia.dto.NoticiaDTO;
import com.example.demo.noticia.model.Noticia;
import com.example.demo.noticia.service.NoticiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @GetMapping
    public ResponseEntity<List<Noticia>> listarNoticias() {
        return ResponseEntity.ok(noticiaService.listarNoticias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Noticia> obtenerNoticiaPorId(@PathVariable Long id) {
        Noticia noticia = noticiaService.obtenerNoticiaPorId(id);
        if (noticia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noticia);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Noticia> guardarNoticia(
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("fechaPublicacion") String fechaPublicacion,
            @RequestParam(value = "iglesiaId", required = false) Long iglesiaId,
            @RequestPart(value = "imagenPortada", required = false) MultipartFile imagenPortada) {
        try {
            // Crear el DTO manualmente con los parámetros recibidos
            NoticiaDTO noticiaDTO = new NoticiaDTO();
            noticiaDTO.setTitulo(titulo);
            noticiaDTO.setContenido(contenido);
            noticiaDTO.setFechaPublicacion(java.time.LocalDate.parse(fechaPublicacion));
            noticiaDTO.setIglesiaId(iglesiaId);

            Noticia noticia = noticiaService.guardarNoticia(noticiaDTO, imagenPortada);
            return ResponseEntity.ok(noticia);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Noticia> actualizarNoticia(
            @PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("fechaPublicacion") String fechaPublicacion,
            @RequestParam(value = "iglesiaId", required = false) Long iglesiaId,
            @RequestPart(value = "nuevaImagenPortada", required = false) MultipartFile nuevaImagenPortada) {
        try {
            // Crear el DTO manualmente con los parámetros recibidos
            NoticiaDTO noticiaDTO = new NoticiaDTO();
            noticiaDTO.setTitulo(titulo);
            noticiaDTO.setContenido(contenido);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            noticiaDTO.setFechaPublicacion(java.time.LocalDate.parse(fechaPublicacion, formatter));
            noticiaDTO.setIglesiaId(iglesiaId);

            // Llamar al servicio para actualizar la noticia
            Noticia noticia = noticiaService.actualizarNoticia(id, noticiaDTO, nuevaImagenPortada);
            if (noticia == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(noticia);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNoticia(@PathVariable Long id) {
        noticiaService.eliminarNoticia(id);
        return ResponseEntity.noContent().build();
    }
}
