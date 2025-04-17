package com.example.demo.informacion.repository;

import com.example.demo.informacion.model.InformacionInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformacionInstitucionalRepository extends JpaRepository<InformacionInstitucional, Long> {
    List<InformacionInstitucional> findByIglesiaId(Long iglesiaId);
    
    // Podemos agregar un método para obtener la información más reciente
    InformacionInstitucional findTopByIglesiaIdOrderByIdDesc(Long iglesiaId);
}
