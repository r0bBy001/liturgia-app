package com.example.demo.acto_liturgico.controller;

import com.example.demo.acto_liturgico.model.ActoLiturgico;
import com.example.demo.acto_liturgico.service.ActoLiturgicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/actos-liturgicos")
public class ActoLiturgicoController {

    @Autowired
    private ActoLiturgicoService service;

    @GetMapping
    public List<ActoLiturgico> getAll(){
        return service.getAll();
    }
    
    @GetMapping("/{id}")
    public ActoLiturgico getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    public ActoLiturgico create(@RequestBody ActoLiturgico actoLiturgico){
        return service.save(actoLiturgico);
    }

    @PutMapping("/{id}")
    public ActoLiturgico update(@PathVariable Long id,@RequestBody ActoLiturgico actoLiturgico){
        return service.update(id, actoLiturgico);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/rango")
    public ResponseEntity<List<ActoLiturgico>> getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(service.getByDateRange(inicio, fin));
    }

    @GetMapping("/iglesia/{iglesiaId}")
    public ResponseEntity<List<ActoLiturgico>> getByIglesia(@PathVariable Long iglesiaId) {
        return ResponseEntity.ok(service.getByIglesia(iglesiaId));
    }

    @GetMapping("/iglesia/{iglesiaId}/rango")
    public ResponseEntity<List<ActoLiturgico>> getByIglesiaAndDateRange(
        @PathVariable Long iglesiaId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(service.getByIglesiaAndDateRange(iglesiaId, inicio, fin));
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<ActoLiturgico>> getByTipo(@PathVariable Long tipoId) {
        return ResponseEntity.ok(service.getByTipo(tipoId));
    }

    @GetMapping("/tipo/{tipoId}/iglesia/{iglesiaId}")
    public ResponseEntity<List<ActoLiturgico>> getByTipoAndIglesia(
        @PathVariable Long tipoId, 
        @PathVariable Long iglesiaId) {
        return ResponseEntity.ok(service.getByTipoAndIglesia(tipoId, iglesiaId));
    }
}
