package com.example.demo.controller;

import com.example.demo.model.Rol;
import com.example.demo.usuario.model.Usuario;
import com.example.demo.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setup")
public class SetupController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create-superadmin")
    public String createSuperAdmin() {
        if (usuarioRepository.count() == 0) {
            Usuario superAdmin = new Usuario();
            superAdmin.setUsername("admin");
            superAdmin.setPassword(passwordEncoder.encode("admin123"));
            superAdmin.setRol(Rol.SUPERADMIN);
            usuarioRepository.save(superAdmin);
            return "Usuario SUPERADMIN creado: admin / admin123";
        }
        return "Ya existen usuarios en el sistema.";
    }
}
