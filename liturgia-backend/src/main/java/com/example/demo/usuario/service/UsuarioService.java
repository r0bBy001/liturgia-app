package com.example.demo.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.usuario.dto.UsuarioDTO;
import com.example.demo.usuario.model.Usuario;
import com.example.demo.usuario.repository.UsuarioRepository;
import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.Iglesia.repository.IglesiaRepository;
import com.example.demo.model.Rol; // Asegúrate de importar el enum Rol

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private IglesiaRepository iglesiaRepository;

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
        Iglesia iglesia = null;

        // Verificar si el rol es "ENCARGADO" y si se proporcionó una iglesia
        if (usuarioDTO.getRol() == Rol.ENCARGADO) { // Comparación directa con el enum
            if (usuarioDTO.getIglesiaId() == null) {
                throw new IllegalArgumentException("Un encargado debe tener una iglesia asignada");
            }
            iglesia = iglesiaRepository.findById(usuarioDTO.getIglesiaId())
                    .orElseThrow(() -> new RuntimeException("Iglesia no encontrada"));
        }

        Usuario usuario = new Usuario(
                usuarioDTO.getUsername(),
                usuarioDTO.getPassword(),
                usuarioDTO.getRol(), // Aquí se pasa el enum directamente
                iglesia
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
        Usuario existingUsuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existingUsuario.setUsername(usuarioDTO.getUsername());
        existingUsuario.setRol(usuarioDTO.getRol());

        // Manejar la relación con la iglesia si el rol es "ENCARGADO"
        if (usuarioDTO.getRol() == Rol.ENCARGADO) { // Comparación directa con el enum
            if (usuarioDTO.getIglesiaId() == null) {
                throw new IllegalArgumentException("Un encargado debe tener una iglesia asignada");
            }
            Iglesia iglesia = iglesiaRepository.findById(usuarioDTO.getIglesiaId())
                    .orElseThrow(() -> new RuntimeException("Iglesia no encontrada"));
            existingUsuario.setIglesia(iglesia);
        } else {
            existingUsuario.setIglesia(null); // Si no es encargado, eliminar la relación con la iglesia
        }

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            existingUsuario.setPassword(usuarioDTO.getPassword());
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
