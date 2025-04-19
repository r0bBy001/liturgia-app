package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    
    private final List<String> publicPaths = Arrays.asList(
            "/api/auth", 
            "/swagger-ui",
            "/v3/api-docs",
            "/uploads"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getServletPath();
        
        for (String publicPath : publicPaths) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        
        if (request.getMethod().equals("GET")) {
            return path.startsWith("/api/iglesias") || 
                   path.startsWith("/api/informacion-institucional") ||
                   path.startsWith("/api/galerias") ||
                   path.startsWith("/api/eventos") ||
                   path.startsWith("/api/noticias") ||
                   path.startsWith("/api/banners") ||
                   path.startsWith("/api/contactos");
        }
        
        return false;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
            
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                String role = jwtUtil.extractRole(jwt);
                Long iglesiaId = jwtUtil.extractIglesiaId(jwt);
                
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                Map<String, Object> details = new HashMap<>();
                if (iglesiaId != null) {
                    details.put("iglesiaId", iglesiaId);
                }

                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );
                
                authenticationToken.setDetails(details);
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
