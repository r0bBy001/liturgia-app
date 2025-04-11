package com.example.demo.contacto.repository;

import com.example.demo.contacto.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByIglesiaId(Long iglesiaId);
}
