package com.example.demo.informacion.controller;

import com.example.demo.informacion.model.InformacionInstitucional;
import com.example.demo.informacion.service.InformacionInstitucionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/informacion-institucional")
public class InformacionInstitucionalController {

    private final InformacionInstitucionalService informacionInstitucionalService;

    public InformacionInstitucionalController(InformacionInstitucionalService informacionInstitucionalService) {
        this.informacionInstitucionalService = informacionInstitucionalService;
    }

    @PostMapping
    public ResponseEntity<InformacionInstitucional> guardarInformacion(@RequestBody InformacionInstitucional informacion) {
        InformacionInstitucional nuevaInformacion = informacionInstitucionalService.guardarInformacion(informacion);
        return ResponseEntity.ok(nuevaInformacion);
    }

    @GetMapping("/iglesia/{iglesiaId}")
    public ResponseEntity<InformacionInstitucional> obtenerInformacionPorIglesia(@PathVariable Long iglesiaId) {
        InformacionInstitucional informacion = informacionInstitucionalService.obtenerInformacionPorIglesia(iglesiaId);
        if (informacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(informacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformacionInstitucional> obtenerInformacionPorId(@PathVariable Long id) {
        Optional<InformacionInstitucional> informacion = informacionInstitucionalService.obtenerInformacionPorId(id);
        return informacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInformacion(@PathVariable Long id) {
        informacionInstitucionalService.eliminarInformacion(id);
        return ResponseEntity.noContent().build();
    }
}
