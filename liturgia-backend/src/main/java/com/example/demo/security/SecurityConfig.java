package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http)) // Habilitar CORS en Spring Security
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/setup/**").permitAll()
                .requestMatchers("/api/iglesias/**").permitAll() 
                .requestMatchers("/api/actos-liturgicos/**").permitAll()
                .requestMatchers("/api/galerias/**").permitAll()
                .requestMatchers("/api/eventos/**").permitAll()
                .requestMatchers("/api/noticias/**").permitAll()
                .requestMatchers("/api/banners/**").permitAll()
                .requestMatchers("/api/contactos/**").permitAll()

                .requestMatchers("/api/usuarios/**").hasRole("SUPERADMIN")
                .requestMatchers("/api/padres/**").hasRole("SUPERADMIN")
                .requestMatchers("/api/iglesias/admin/**").hasRole("SUPERADMIN")

                .requestMatchers(
                    "/api/auth/**", 
                    "/swagger-ui/**", 
                    "/v3/api-docs/**"
                ).permitAll()

                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
