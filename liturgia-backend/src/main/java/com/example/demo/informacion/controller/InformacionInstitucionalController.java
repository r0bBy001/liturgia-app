package com.example.demo.informacion.controller;

import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.Iglesia.repository.IglesiaRepository;
import com.example.demo.informacion.dto.InformacionInstitucionalDTO;
import com.example.demo.informacion.model.InformacionInstitucional;
import com.example.demo.informacion.service.InformacionInstitucionalService;
import com.example.demo.security.JwtContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/informacion-institucional")
public class InformacionInstitucionalController {

    private final InformacionInstitucionalService informacionInstitucionalService;
    private final IglesiaRepository iglesiaRepository;

    public InformacionInstitucionalController(InformacionInstitucionalService informacionInstitucionalService,
                                           IglesiaRepository iglesiaRepository) {
        this.informacionInstitucionalService = informacionInstitucionalService;
        this.iglesiaRepository = iglesiaRepository;
    }

    @GetMapping
    public ResponseEntity<List<InformacionInstitucionalDTO>> obtenerTodasLasInformaciones() {
        List<InformacionInstitucional> informaciones = informacionInstitucionalService.obtenerTodasLasInformaciones();
        
        List<InformacionInstitucionalDTO> informacionesDTO = informaciones.stream()
            .map(info -> convertirADTO(info))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(informacionesDTO);
    }

    @PostMapping
    public ResponseEntity<?> guardarInformacion(@RequestBody InformacionInstitucionalDTO informacionDTO) {
        // Verificar si el usuario es un encargado y tiene acceso a esta iglesia
        Long iglesiaIdDelToken = JwtContext.getCurrentIglesiaId();
        
        if (JwtContext.hasRole("ENCARGADO") && 
                (iglesiaIdDelToken == null || !iglesiaIdDelToken.equals(informacionDTO.getIglesiaId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para crear información para esta iglesia");
        }
        
        Optional<Iglesia> iglesiaOpt = iglesiaRepository.findById(informacionDTO.getIglesiaId());
        if (!iglesiaOpt.isPresent()) {
            return ResponseEntity.badRequest().body("La iglesia especificada no existe");
        }
        
        InformacionInstitucional informacion = new InformacionInstitucional();
        informacion.setDescripcion(informacionDTO.getDescripcion());
        informacion.setHistoria(informacionDTO.getHistoria());
        informacion.setMision(informacionDTO.getMision());
        informacion.setVision(informacionDTO.getVision());
        informacion.setIglesia(iglesiaOpt.get());
        
        InformacionInstitucional nuevaInformacion = informacionInstitucionalService.guardarInformacion(informacion);
        return ResponseEntity.ok(convertirADTO(nuevaInformacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInformacion(@PathVariable Long id, 
                                                @RequestBody InformacionInstitucionalDTO informacionDTO) {
        Optional<InformacionInstitucional> informacionExistente = informacionInstitucionalService.obtenerInformacionPorId(id);
        
        if (!informacionExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        InformacionInstitucional informacion = informacionExistente.get();
        
        // Verificar si el usuario es un encargado y tiene acceso a esta iglesia
        Long iglesiaIdDelToken = JwtContext.getCurrentIglesiaId();
        
        if (JwtContext.hasRole("ENCARGADO") && 
                (iglesiaIdDelToken == null || !iglesiaIdDelToken.equals(informacion.getIglesia().getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para modificar información de esta iglesia");
        }
        
        // Actualizar campos
        informacion.setDescripcion(informacionDTO.getDescripcion());
        informacion.setHistoria(informacionDTO.getHistoria());
        informacion.setMision(informacionDTO.getMision());
        informacion.setVision(informacionDTO.getVision());
        
        InformacionInstitucional informacionActualizada = informacionInstitucionalService.guardarInformacion(informacion);
        return ResponseEntity.ok(convertirADTO(informacionActualizada));
    }

    @GetMapping("/iglesia/{iglesiaId}")
    public ResponseEntity<InformacionInstitucionalDTO> obtenerInformacionPorIglesia(@PathVariable Long iglesiaId) {
        InformacionInstitucional informacion = informacionInstitucionalService.obtenerInformacionPorIglesia(iglesiaId);
        if (informacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertirADTO(informacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformacionInstitucionalDTO> obtenerInformacionPorId(@PathVariable Long id) {
        Optional<InformacionInstitucional> informacion = informacionInstitucionalService.obtenerInformacionPorId(id);
        return informacion.map(info -> ResponseEntity.ok(convertirADTO(info)))
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInformacion(@PathVariable Long id) {
        Optional<InformacionInstitucional> informacionOpt = informacionInstitucionalService.obtenerInformacionPorId(id);
        
        if (!informacionOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        InformacionInstitucional informacion = informacionOpt.get();
        
        // Verificar si el usuario es un encargado y tiene acceso a esta iglesia
        Long iglesiaIdDelToken = JwtContext.getCurrentIglesiaId();
        
        if (JwtContext.hasRole("ENCARGADO") && 
                (iglesiaIdDelToken == null || !iglesiaIdDelToken.equals(informacion.getIglesia().getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para eliminar información de esta iglesia");
        }
        
        informacionInstitucionalService.eliminarInformacion(id);
        return ResponseEntity.noContent().build();
    }
    
    // Método auxiliar para convertir entidades a DTOs
    private InformacionInstitucionalDTO convertirADTO(InformacionInstitucional informacion) {
        InformacionInstitucionalDTO dto = new InformacionInstitucionalDTO();
        dto.setId(informacion.getId());
        dto.setDescripcion(informacion.getDescripcion());
        dto.setHistoria(informacion.getHistoria());
        dto.setMision(informacion.getMision());
        dto.setVision(informacion.getVision());
        dto.setIglesiaId(informacion.getIglesia().getId());
        return dto;
    }
}
