package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/iglesias/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/iglesias/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/iglesias/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/iglesias/**").hasRole("SUPERADMIN")
                .requestMatchers("/api/padres/**", "/api/usuarios/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.GET, "/api/galerias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/noticias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/banners/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/contactos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/galerias/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/galerias/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/galerias/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.POST, "/api/eventos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/eventos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.POST, "/api/noticias/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/noticias/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/noticias/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.POST, "/api/banners/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/banners/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/banners/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.POST, "/api/contactos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/contactos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/contactos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/uploads/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
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
