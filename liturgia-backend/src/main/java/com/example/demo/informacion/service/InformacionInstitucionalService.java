package com.example.demo.informacion.service;

import com.example.demo.informacion.model.InformacionInstitucional;
import com.example.demo.informacion.repository.InformacionInstitucionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformacionInstitucionalService {

    @Autowired
    private InformacionInstitucionalRepository informacionInstitucionalRepository;

    public InformacionInstitucional guardarInformacion(InformacionInstitucional informacion) {
        return informacionInstitucionalRepository.save(informacion);
    }

    public List<InformacionInstitucional> obtenerTodasLasInformaciones() {
        return informacionInstitucionalRepository.findAll();
    }

    // Modificar método para obtener la información única
    public InformacionInstitucional obtenerInformacionPorIglesia(Long iglesiaId) {
        return informacionInstitucionalRepository.findByIglesiaId(iglesiaId)
                .orElse(null);
    }
    
    public List<InformacionInstitucional> obtenerTodasInformacionesPorIglesia(Long iglesiaId) {
        // Obtiene el Optional
        Optional<InformacionInstitucional> infoOptional = informacionInstitucionalRepository.findByIglesiaId(iglesiaId);
        
        // Lo convierte a una lista (vacía si no existe, con un elemento si existe)
        return infoOptional.map(List::of).orElse(List.of());
    }

    public Optional<InformacionInstitucional> obtenerInformacionPorId(Long id) {
        return informacionInstitucionalRepository.findById(id);
    }

    public void eliminarInformacion(Long id) {
        informacionInstitucionalRepository.deleteById(id);
    }

    // Método para guardar o actualizar información
    public InformacionInstitucional guardarOActualizarInformacion(InformacionInstitucional informacion) {
        // Verificar si ya existe información para esta iglesia
        InformacionInstitucional infoExistente = obtenerInformacionPorIglesia(informacion.getIglesia().getId());
        
        if (infoExistente != null) {
            // Actualizar los campos existentes
            infoExistente.setDescripcion(informacion.getDescripcion());
            infoExistente.setHistoria(informacion.getHistoria());
            infoExistente.setMision(informacion.getMision());
            infoExistente.setVision(informacion.getVision());
            // otros campos que puedan existir
            
            return informacionInstitucionalRepository.save(infoExistente);
        } else {
            // Crear nuevo registro si no existe
            return informacionInstitucionalRepository.save(informacion);
        }
    }
}
