package com.example.demo.galeria.repository;

import com.example.demo.galeria.model.Galeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GaleriaRepository extends JpaRepository<Galeria, Long> {
}
