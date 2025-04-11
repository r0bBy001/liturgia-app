package com.example.demo.padre.controller;

import com.example.demo.padre.model.Padre;
import com.example.demo.padre.service.PadreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/padres")
public class PadreController {

    private final PadreService padreService;

    public PadreController(PadreService padreService) {
        this.padreService = padreService;
    }

    @GetMapping
    public ResponseEntity<List<Padre>> listarPadres() {
        return ResponseEntity.ok(padreService.listarPadres());
    }

    @GetMapping("/iglesia/{iglesiaId}")
    public ResponseEntity<List<Padre>> listarPadresPorIglesia(@PathVariable Long iglesiaId) {
        return ResponseEntity.ok(padreService.listarPadresPorIglesia(iglesiaId));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Padre> guardarPadre(
            @RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("iglesiaId") Long iglesiaId,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            Padre padre = new Padre();
            padre.setNombres(nombres);
            padre.setApellidos(apellidos);
            padre.setDescripcion(descripcion);
            padre.setIglesia(new com.example.demo.Iglesia.model.Iglesia());
            padre.getIglesia().setId(iglesiaId);

            Padre nuevoPadre = padreService.guardarPadre(padre, foto);
            return ResponseEntity.ok(nuevoPadre);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Padre> obtenerPadrePorId(@PathVariable Long id) {
        Padre padre = padreService.obtenerPadrePorId(id);
        if (padre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(padre);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Padre> actualizarPadre(
            @PathVariable Long id,
            @RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("iglesiaId") Long iglesiaId,
            @RequestPart(value = "nuevaFoto", required = false) MultipartFile nuevaFoto) {
        try {
            Padre padre = new Padre();
            padre.setNombres(nombres);
            padre.setApellidos(apellidos);
            padre.setDescripcion(descripcion);
            padre.setIglesia(new com.example.demo.Iglesia.model.Iglesia());
            padre.getIglesia().setId(iglesiaId);

            Padre padreActualizado = padreService.actualizarPadre(id, padre, nuevaFoto);
            if (padreActualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(padreActualizado);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPadre(@PathVariable Long id) {
        padreService.eliminarPadre(id);
        return ResponseEntity.noContent().build();
    }
}
