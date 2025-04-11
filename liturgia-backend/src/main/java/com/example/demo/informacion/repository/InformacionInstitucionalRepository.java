package com.example.demo.informacion.repository;

import com.example.demo.informacion.model.InformacionInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacionInstitucionalRepository extends JpaRepository<InformacionInstitucional, Long> {
    InformacionInstitucional findByIglesiaId(Long iglesiaId);
}
