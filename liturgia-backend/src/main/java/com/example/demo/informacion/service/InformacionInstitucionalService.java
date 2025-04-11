package com.example.demo.informacion.service;

import com.example.demo.informacion.model.InformacionInstitucional;
import com.example.demo.informacion.repository.InformacionInstitucionalRepository;
import org.springframework.stereotype.Service;

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

    public InformacionInstitucional obtenerInformacionPorIglesia(Long iglesiaId) {
        return informacionInstitucionalRepository.findByIglesiaId(iglesiaId);
    }

    public Optional<InformacionInstitucional> obtenerInformacionPorId(Long id) {
        return informacionInstitucionalRepository.findById(id);
    }

    public void eliminarInformacion(Long id) {
        informacionInstitucionalRepository.deleteById(id);
    }
}
