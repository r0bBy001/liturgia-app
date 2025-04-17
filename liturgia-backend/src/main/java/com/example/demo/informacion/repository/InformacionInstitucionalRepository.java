package com.example.demo.informacion.repository;

import com.example.demo.informacion.model.InformacionInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformacionInstitucionalRepository extends JpaRepository<InformacionInstitucional, Long> {
    // Modificar para devolver Optional<InformacionInstitucional> en vez de List
    Optional<InformacionInstitucional> findByIglesiaId(Long iglesiaId);
}
