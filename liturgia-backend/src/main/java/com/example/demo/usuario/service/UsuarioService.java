package com.example.demo.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.usuario.dto.UsuarioDTO;
import com.example.demo.usuario.model.Usuario;
import com.example.demo.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<UsuarioDTO> getAll() {
        return repository.findAll().stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getUsername(),
                        null, // No devolver el password por seguridad
                        usuario.getRol(),
                        usuario.getIglesia() != null ? usuario.getIglesia().getId() : null
                ))
                .collect(Collectors.toList());
    }

    public UsuarioDTO getById(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                null, // No devolver el password por seguridad
                usuario.getRol(),
                usuario.getIglesia() != null ? usuario.getIglesia().getId() : null
        );
    }

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(
                usuarioDTO.getUsername(),
                usuarioDTO.getPassword(), // El modelo se encargará de encriptar la contraseña
                usuarioDTO.getRol(),
                null // Manejar la relación con Iglesia si es necesario
        );
        Usuario savedUsuario = repository.save(usuario);
        return new UsuarioDTO(
                savedUsuario.getId(),
                savedUsuario.getUsername(),
                null, // No devolver el password por seguridad
                savedUsuario.getRol(),
                savedUsuario.getIglesia() != null ? savedUsuario.getIglesia().getId() : null
        );
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        Usuario existingUsuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existingUsuario.setUsername(usuarioDTO.getUsername());
        existingUsuario.setRol(usuarioDTO.getRol());
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            existingUsuario.setPassword(usuarioDTO.getPassword()); // El modelo se encargará de encriptar la contraseña
        }
        Usuario updatedUsuario = repository.save(existingUsuario);
        return new UsuarioDTO(
                updatedUsuario.getId(),
                updatedUsuario.getUsername(),
                null, // No devolver el password por seguridad
                updatedUsuario.getRol(),
                updatedUsuario.getIglesia() != null ? updatedUsuario.getIglesia().getId() : null
        );
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        repository.deleteById(id);
    }
}
