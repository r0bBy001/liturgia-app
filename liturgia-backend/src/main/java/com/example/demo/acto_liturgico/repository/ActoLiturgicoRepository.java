package com.example.demo.acto_liturgico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.acto_liturgico.model.ActoLiturgico;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ActoLiturgicoRepository extends JpaRepository<ActoLiturgico, Long> {
    Optional<ActoLiturgico> findByIglesiaIdAndFechaHora(Long iglesiaId, LocalDateTime fechaHora);
}
