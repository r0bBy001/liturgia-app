package com.example.demo.galeria.service;

import com.example.demo.galeria.model.ImagenGaleria;
import com.example.demo.galeria.repository.ImagenGaleriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenGaleriaService {

    private final ImagenGaleriaRepository imagenGaleriaRepository;

    public ImagenGaleriaService(ImagenGaleriaRepository imagenGaleriaRepository) {
        this.imagenGaleriaRepository = imagenGaleriaRepository;
    }

    public List<ImagenGaleria> listarImagenesPorGaleria(Long galeriaId) {
        return imagenGaleriaRepository.findByGaleriaId(galeriaId);
    }

    public ImagenGaleria guardarImagen(ImagenGaleria imagenGaleria) {
        if (imagenGaleria.isEsPortada()) {
            long portadas = imagenGaleriaRepository.countByGaleriaIdAndEsPortadaTrue(imagenGaleria.getGaleria().getId());
            if (portadas > 0) {
                throw new IllegalArgumentException("Solo puede haber una portada por galería.");
            }
        }
        return imagenGaleriaRepository.save(imagenGaleria);
    }

    public void eliminarImagen(Long id) {
        imagenGaleriaRepository.deleteById(id);
    }
}
