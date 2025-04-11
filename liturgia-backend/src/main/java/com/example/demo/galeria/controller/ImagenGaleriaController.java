package com.example.demo.galeria.controller;

import com.example.demo.galeria.model.ImagenGaleria;
import com.example.demo.galeria.service.ImagenGaleriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes-galeria")
public class ImagenGaleriaController {

    private final ImagenGaleriaService imagenGaleriaService;

    public ImagenGaleriaController(ImagenGaleriaService imagenGaleriaService) {
        this.imagenGaleriaService = imagenGaleriaService;
    }

    @GetMapping("/{galeriaId}")
    public ResponseEntity<List<ImagenGaleria>> listarImagenesPorGaleria(@PathVariable Long galeriaId) {
        return ResponseEntity.ok(imagenGaleriaService.listarImagenesPorGaleria(galeriaId));
    }

    @PostMapping
    public ResponseEntity<ImagenGaleria> guardarImagen(@RequestBody ImagenGaleria imagenGaleria) {
        try {
            ImagenGaleria nuevaImagen = imagenGaleriaService.guardarImagen(imagenGaleria);
            return ResponseEntity.ok(nuevaImagen);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Validación de portada
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Long id) {
        imagenGaleriaService.eliminarImagen(id);
        return ResponseEntity.noContent().build();
    }
}
