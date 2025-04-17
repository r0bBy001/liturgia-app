package com.example.demo.informacion.service;

import com.example.demo.informacion.model.InformacionInstitucional;
import com.example.demo.informacion.repository.InformacionInstitucionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformacionInstitucionalService {

    private final InformacionInstitucionalRepository informacionInstitucionalRepository;

    public InformacionInstitucionalService(InformacionInstitucionalRepository informacionInstitucionalRepository) {
        this.informacionInstitucionalRepository = informacionInstitucionalRepository;
    }

    public InformacionInstitucional guardarInformacion(InformacionInstitucional informacion) {
        return informacionInstitucionalRepository.save(informacion);
    }

    public List<InformacionInstitucional> obtenerTodasLasInformaciones() {
        return informacionInstitucionalRepository.findAll();
    }

    public InformacionInstitucional obtenerInformacionPorIglesia(Long iglesiaId) {
        // Usamos el nuevo método que obtiene la información más reciente
        return informacionInstitucionalRepository.findTopByIglesiaIdOrderByIdDesc(iglesiaId);
    }
    
    public List<InformacionInstitucional> obtenerTodasInformacionesPorIglesia(Long iglesiaId) {
        // Devuelve todas las informaciones de una iglesia (podría ser útil para históricos)
        return informacionInstitucionalRepository.findByIglesiaId(iglesiaId);
    }

    public Optional<InformacionInstitucional> obtenerInformacionPorId(Long id) {
        return informacionInstitucionalRepository.findById(id);
    }

    public void eliminarInformacion(Long id) {
        informacionInstitucionalRepository.deleteById(id);
    }
}
