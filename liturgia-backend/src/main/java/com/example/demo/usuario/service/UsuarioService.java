package com.example.demo.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.usuario.dto.UsuarioDTO;
import com.example.demo.usuario.model.Usuario;
import com.example.demo.usuario.repository.UsuarioRepository;
import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.Iglesia.repository.IglesiaRepository;
import com.example.demo.model.Rol; // Asegúrate de importar el enum Rol
import com.example.demo.exception.UsuarioNotFoundException;
import com.example.demo.exception.IglesiaNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private IglesiaRepository iglesiaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
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
        if (usuarioDTO.getRol() == Rol.ENCARGADO) {
            if (usuarioDTO.getIglesiaId() == null) {
                throw new IllegalArgumentException("Un encargado debe tener una iglesia asignada");
            }
            iglesia = iglesiaRepository.findById(usuarioDTO.getIglesiaId())
                    .orElseThrow(() -> new IglesiaNotFoundException("Iglesia no encontrada"));
        }

        // Validar que el password no sea null o vacío
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }

        // Encriptar la contraseña antes de guardar
        String encodedPassword = passwordEncoder.encode(usuarioDTO.getPassword());

        Usuario usuario = new Usuario(
                usuarioDTO.getUsername(),
                encodedPassword, // Guardar la contraseña encriptada
                usuarioDTO.getRol(),
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
        if (usuarioDTO.getRol() == Rol.ENCARGADO) {
            if (usuarioDTO.getIglesiaId() == null) {
                throw new IllegalArgumentException("Un encargado debe tener una iglesia asignada");
            }
            Iglesia iglesia = iglesiaRepository.findById(usuarioDTO.getIglesiaId())
                    .orElseThrow(() -> new RuntimeException("Iglesia no encontrada"));
            existingUsuario.setIglesia(iglesia);
        } else {
            existingUsuario.setIglesia(null); // Si no es encargado, eliminar la relación con la iglesia
        }

        // Encriptar la contraseña si se proporciona
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(usuarioDTO.getPassword());
            existingUsuario.setPassword(encodedPassword);
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
