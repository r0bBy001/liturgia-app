package com.example.demo.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Usuario.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
