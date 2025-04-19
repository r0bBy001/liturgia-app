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
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> crearOActualizarInformacion(@RequestBody InformacionInstitucionalDTO informacionDTO) {
        // Verificar si el usuario es un encargado
        if (!JwtContext.hasRole("ENCARGADO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Solo los encargados pueden gestionar la información institucional");
        }
        
        Long iglesiaId = JwtContext.getCurrentIglesiaId();
        if (iglesiaId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No tienes una iglesia asignada");
        }
        
        Optional<Iglesia> iglesiaOpt = iglesiaRepository.findById(iglesiaId);
        if (!iglesiaOpt.isPresent()) {
            return ResponseEntity.badRequest().body("La iglesia especificada no existe");
        }
        
        // Convertir DTO a entidad
        InformacionInstitucional informacion = convertirAEntidad(informacionDTO);
        informacion.setIglesia(iglesiaOpt.get());
        
        // Guardar o actualizar
        InformacionInstitucional resultado = informacionInstitucionalService.guardarOActualizarInformacion(informacion);
        
        return ResponseEntity.ok(convertirADTO(resultado));
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
    public ResponseEntity<?> obtenerInformacionPorIglesia(@PathVariable Long iglesiaId) {
        InformacionInstitucional informacion = informacionInstitucionalService.obtenerInformacionPorIglesia(iglesiaId);
        
        if (informacion == null) {
            // Verificar si la iglesia existe
            boolean iglesiaExiste = iglesiaRepository.existsById(iglesiaId);
            
            if (!iglesiaExiste) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró ninguna iglesia con el ID proporcionado.");
            }
            
            // La iglesia existe, pero no tiene información configurada aún
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Esta iglesia aún no tiene información institucional configurada.");
            respuesta.put("iglesiaId", iglesiaId);
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
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

    // Método auxiliar para convertir DTOs a entidades
    private InformacionInstitucional convertirAEntidad(InformacionInstitucionalDTO dto) {
        InformacionInstitucional informacion = new InformacionInstitucional();
        informacion.setDescripcion(dto.getDescripcion());
        informacion.setHistoria(dto.getHistoria());
        informacion.setMision(dto.getMision());
        informacion.setVision(dto.getVision());
        return informacion;
    }
}
