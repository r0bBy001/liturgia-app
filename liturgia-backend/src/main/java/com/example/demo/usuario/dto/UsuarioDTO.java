package com.example.demo.usuario.dto;

import com.example.demo.model.Rol;

public class UsuarioDTO {
    private Long id;
    private String username;
    private String password; // Solo para solicitudes
    private Rol rol;
    private Long iglesiaId;

    public UsuarioDTO() {}

    // Constructor para respuestas (sin password)
    public UsuarioDTO(Long id, String username, Rol rol, Long iglesiaId) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.iglesiaId = iglesiaId;
    }

    // Constructor para solicitudes (con password)
    public UsuarioDTO(Long id, String username, String password, Rol rol, Long iglesiaId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.iglesiaId = iglesiaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(Long iglesiaId) {
        this.iglesiaId = iglesiaId;
    }
}
