package com.example.demo.tipo_acto_liturgico.controller;

import com.example.demo.tipo_acto_liturgico.model.TipoActoLiturgico;
import com.example.demo.tipo_acto_liturgico.service.TipoActoLiturgicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-actos")
public class TipoActoLiturgicoController {
    
    @Autowired
    private TipoActoLiturgicoService tipoActoLiturgicoService;

    @GetMapping
    public List<TipoActoLiturgico> getAllTipos(){
        return tipoActoLiturgicoService.getAllTipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoActoLiturgico> getTipoById(@PathVariable Long id) {
        TipoActoLiturgico tipoActoLiturgico = tipoActoLiturgicoService.getTipoById(id);
        if (tipoActoLiturgico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoActoLiturgico);
    }
    
    @PostMapping
    public ResponseEntity<TipoActoLiturgico> createTipoActoLiturgico(@RequestBody TipoActoLiturgico tipoActoLiturgico) {
        if (tipoActoLiturgico.getDescripcion() == null || tipoActoLiturgico.getDescripcion().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        TipoActoLiturgico nuevoTipo = tipoActoLiturgicoService.saveTipoActoLiturgico(tipoActoLiturgico);
        return ResponseEntity.ok(nuevoTipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoActoLiturgico> updateTipoActoLiturgico(@PathVariable Long id, @RequestBody TipoActoLiturgico tipoActoLiturgico) {
        TipoActoLiturgico tipoExistente = tipoActoLiturgicoService.getTipoById(id);
        if (tipoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        tipoActoLiturgico.setId(id);
        TipoActoLiturgico actualizado = tipoActoLiturgicoService.updateTipoActoLiturgico(id, tipoActoLiturgico);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoActoLiturgico(@PathVariable Long id) {
        TipoActoLiturgico tipoActoLiturgico = tipoActoLiturgicoService.getTipoById(id);
        if (tipoActoLiturgico == null) {
            return ResponseEntity.notFound().build();
        }

        tipoActoLiturgicoService.deleteTipoActoLiturgico(id);
        return ResponseEntity.noContent().build();
    }
}
