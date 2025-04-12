package com.example.demo.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.usuario.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
