package com.example.demo.acto_liturgico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.acto_liturgico.model.ActoLiturgico;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface ActoLiturgicoRepository extends JpaRepository<ActoLiturgico, Long> {
    Optional<ActoLiturgico> findByIglesiaIdAndFechaHora(Long iglesiaId, LocalDateTime fechaHora);
    List<ActoLiturgico> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<ActoLiturgico> findByIglesiaId(Long iglesiaId);
    List<ActoLiturgico> findByIglesiaIdAndFechaHoraBetween(Long iglesiaId, LocalDateTime inicio, LocalDateTime fin);
    List<ActoLiturgico> findByTipoId(Long tipoId);
    List<ActoLiturgico> findByTipoIdAndIglesiaId(Long tipoId, Long iglesiaId);
}
