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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/uploads/**", "/api/setup/create-superadmin").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/iglesias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/informacion-institucional/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/galerias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/noticias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/banners/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/contactos/**").permitAll()     
                .requestMatchers(HttpMethod.GET, "/api/actos-liturgicos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/tipos-actos/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/informacion-institucional/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/informacion-institucional/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/informacion-institucional/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                
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
                
                .requestMatchers(HttpMethod.POST, "/api/actos-liturgicos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.PUT, "/api/actos-liturgicos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                .requestMatchers(HttpMethod.DELETE, "/api/actos-liturgicos/**").hasAnyRole("SUPERADMIN", "ENCARGADO")
                
                .requestMatchers(HttpMethod.POST, "/api/iglesias/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/iglesias/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/iglesias/**").hasRole("SUPERADMIN")
                
                .requestMatchers(HttpMethod.POST, "/api/tipos-actos/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/tipos-actos/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/tipos-actos/**").hasRole("SUPERADMIN")
                
                .requestMatchers("/api/padres/**", "/api/usuarios/**").hasRole("SUPERADMIN")

                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
        configuration.setExposedHeaders(List.of("authorization"));
        configuration.setAllowCredentials(false);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
