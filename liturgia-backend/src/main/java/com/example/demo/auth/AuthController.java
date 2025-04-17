package com.example.demo.auth;

import com.example.demo.model.Rol;
import com.example.demo.security.JwtUtil;
import com.example.demo.usuario.model.Usuario;
import com.example.demo.usuario.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String username = authentication.getName();
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                    .orElse("");

            // Buscar al usuario para obtener información adicional
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token;
            
            // Solo enviar el ID de la iglesia si el usuario es un ENCARGADO
            if (usuario.getRol() == Rol.ENCARGADO && usuario.getIglesia() != null) {
                Long iglesiaId = usuario.getIglesia().getId();
                token = jwtUtil.generateToken(username, role, iglesiaId);
            } else {
                token = jwtUtil.generateToken(username, role);
            }

            // Solo devolver el token en la respuesta
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            AuthResponse errorResponse = new AuthResponse(null);
            errorResponse.setError("Credenciales inválidas");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}
