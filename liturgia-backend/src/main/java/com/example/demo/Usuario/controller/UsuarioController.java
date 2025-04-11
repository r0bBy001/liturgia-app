package com.example.demo.Usuario.controller;

import com.example.demo.Usuario.model.Usuario;
import com.example.demo.Usuario.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario){
        return service.create(usuario);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id,@RequestBody Usuario usuario){
        return service.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
