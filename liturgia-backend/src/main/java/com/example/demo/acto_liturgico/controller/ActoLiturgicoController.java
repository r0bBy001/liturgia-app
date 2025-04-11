package com.example.demo.acto_liturgico.controller;

import com.example.demo.acto_liturgico.model.ActoLiturgico;
import com.example.demo.acto_liturgico.service.ActoLiturgicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
