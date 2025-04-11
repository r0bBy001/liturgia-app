package com.example.demo.Usuario.service;

import com.example.demo.Usuario.model.Usuario;
import com.example.demo.Usuario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> getAll(){
        return repository.findAll();
    }

    public Usuario getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Usuario create(Usuario usuario){
        return repository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario){
        if (repository.existsById(id)) {
            usuario.setId(id);
            return repository.save(usuario);
        }
        return null;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
