package com.example.demo.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.usuario.dto.UsuarioDTO;
import com.example.demo.usuario.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UsuarioDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        return service.create(usuarioDTO);
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return service.update(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
