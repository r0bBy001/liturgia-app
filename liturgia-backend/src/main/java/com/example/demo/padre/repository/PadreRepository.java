package com.example.demo.padre.repository;

import com.example.demo.padre.model.Padre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PadreRepository extends JpaRepository<Padre, Long> {
    List<Padre> findByIglesiaId(Long iglesiaId);
}