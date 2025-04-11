package com.example.demo.galeria.repository;

import com.example.demo.galeria.model.ImagenGaleria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenGaleriaRepository extends JpaRepository<ImagenGaleria, Long> {
    List<ImagenGaleria> findByGaleriaId(Long galeriaId);

    long countByGaleriaIdAndEsPortadaTrue(Long galeriaId);
}
