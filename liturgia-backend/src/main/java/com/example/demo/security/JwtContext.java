package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class JwtContext {
    
    /**
     * Obtiene el ID de la iglesia del usuario autenticado actual
     * @return El ID de la iglesia o null si no está disponible o el usuario no es un ENCARGADO
     */
    public static Long getCurrentIglesiaId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || authentication.getDetails() == null) {
            return null;
        }
        
        try {
            // Agregar anotación para suprimir la advertencia de tipo no comprobado
            @SuppressWarnings("unchecked")
            Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
            return details.containsKey("iglesiaId") ? (Long) details.get("iglesiaId") : null;
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    /**
     * Verifica si el usuario actual tiene un rol específico
     * @param role El rol a verificar (sin el prefijo "ROLE_")
     * @return true si el usuario tiene el rol, false en caso contrario
     */
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
    
    /**
     * Obtiene el nombre de usuario del usuario autenticado actual
     * @return El nombre de usuario o null si no hay usuario autenticado
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        return authentication.getName();
    }
}