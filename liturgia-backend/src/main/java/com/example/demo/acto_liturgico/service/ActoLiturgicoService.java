package com.example.demo.acto_liturgico.service;

import com.example.demo.acto_liturgico.model.ActoLiturgico;
import com.example.demo.acto_liturgico.repository.ActoLiturgicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActoLiturgicoService {
    
    @Autowired
    private ActoLiturgicoRepository repository;
    
    public List<ActoLiturgico> getAll(){
        return repository.findAll();
    }

    public ActoLiturgico getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public ActoLiturgico save(ActoLiturgico actoLiturgico){
        boolean exists = repository.findByIglesiaIdAndFechaHora(
            actoLiturgico.getIglesia().getId(),
            actoLiturgico.getFechaHora()
        ).isPresent();

        if (exists) {
            throw new IllegalArgumentException("Ya existe un acto liturgico en esta iglesia para esa hora.");
        }

        return repository.save(actoLiturgico);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public ActoLiturgico update(Long id,ActoLiturgico updated){
        if (repository.existsById(id)) {
            updated.setId(id);
            return repository.save(updated);
        }
        return null;
    }

    public List<ActoLiturgico> getByDateRange(LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByFechaHoraBetween(inicio, fin);
    }

    public List<ActoLiturgico> getByIglesia(Long iglesiaId) {
        return repository.findByIglesiaId(iglesiaId);
    }

    public List<ActoLiturgico> getByIglesiaAndDateRange(Long iglesiaId, LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByIglesiaIdAndFechaHoraBetween(iglesiaId, inicio, fin);
    }

    public List<ActoLiturgico> getByTipo(Long tipoId) {
        return repository.findByTipoId(tipoId);
    }

    public List<ActoLiturgico> getByTipoAndIglesia(Long tipoId, Long iglesiaId) {
        return repository.findByTipoIdAndIglesiaId(tipoId, iglesiaId);
    }
}
