package com.example.demo.galeria.service;

import com.example.demo.galeria.model.Galeria;
import com.example.demo.galeria.repository.GaleriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GaleriaService {

    private final GaleriaRepository galeriaRepository;

    public GaleriaService(GaleriaRepository galeriaRepository) {
        this.galeriaRepository = galeriaRepository;
    }

    public List<Galeria> listarGalerias() {
        return galeriaRepository.findAll();
    }

    public Galeria guardarGaleria(Galeria galeria) {
        return galeriaRepository.save(galeria);
    }

    public Galeria obtenerGaleriaPorId(Long id) {
        return galeriaRepository.findById(id).orElse(null);
    }

    public void eliminarGaleria(Long id) {
        galeriaRepository.deleteById(id);
    }
}
