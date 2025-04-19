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

    public InformacionInstitucional obtenerInformacionPorIglesia(Long iglesiaId) {
        return informacionInstitucionalRepository.findByIglesiaId(iglesiaId)
                .orElse(null);
    }
    
    public List<InformacionInstitucional> obtenerTodasInformacionesPorIglesia(Long iglesiaId) {
        Optional<InformacionInstitucional> infoOptional = informacionInstitucionalRepository.findByIglesiaId(iglesiaId);
        
        return infoOptional.map(List::of).orElse(List.of());
    }

    public Optional<InformacionInstitucional> obtenerInformacionPorId(Long id) {
        return informacionInstitucionalRepository.findById(id);
    }

    public void eliminarInformacion(Long id) {
        informacionInstitucionalRepository.deleteById(id);
    }

    public InformacionInstitucional guardarOActualizarInformacion(InformacionInstitucional informacion) {
        InformacionInstitucional infoExistente = obtenerInformacionPorIglesia(informacion.getIglesia().getId());
        
        if (infoExistente != null) {
            infoExistente.setDescripcion(informacion.getDescripcion());
            infoExistente.setHistoria(informacion.getHistoria());
            infoExistente.setMision(informacion.getMision());
            infoExistente.setVision(informacion.getVision());
            
            return informacionInstitucionalRepository.save(infoExistente);
        } else {
            return informacionInstitucionalRepository.save(informacion);
        }
    }
}
