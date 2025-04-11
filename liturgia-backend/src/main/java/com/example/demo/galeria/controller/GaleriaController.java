package com.example.demo.galeria.controller;

import com.example.demo.galeria.model.Galeria;
import com.example.demo.galeria.service.GaleriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galerias")
public class GaleriaController {

    private final GaleriaService galeriaService;

    public GaleriaController(GaleriaService galeriaService) {
        this.galeriaService = galeriaService;
    }

    @GetMapping
    public ResponseEntity<List<Galeria>> listarGalerias() {
        return ResponseEntity.ok(galeriaService.listarGalerias());
    }

    @PostMapping
    public ResponseEntity<Galeria> guardarGaleria(@RequestBody Galeria galeria) {
        Galeria nuevaGaleria = galeriaService.guardarGaleria(galeria);
        return ResponseEntity.ok(nuevaGaleria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Galeria> obtenerGaleriaPorId(@PathVariable Long id) {
        Galeria galeria = galeriaService.obtenerGaleriaPorId(id);
        if (galeria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(galeria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGaleria(@PathVariable Long id) {
        galeriaService.eliminarGaleria(id);
        return ResponseEntity.noContent().build();
    }
}
