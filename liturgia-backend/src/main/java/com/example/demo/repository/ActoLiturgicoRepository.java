package com.example.demo.repository;

import com.example.demo.model.ActoLiturgico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ActoLiturgicoRepository extends JpaRepository<ActoLiturgico, Long> {
    Optional<ActoLiturgico> findByIglesiaIdAndFechaHora(Long iglesiaId, LocalDateTime fechaHora);
}
