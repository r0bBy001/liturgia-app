package com.example.demo.contacto.dto;

import java.time.LocalDate;

public class ContactoDTO {

    private Long id;
    private String nombre;
    private String email;
    private String mensaje;
    private LocalDate fechaEnvio;
    private String tipo;
    private Long iglesiaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(Long iglesiaId) {
        this.iglesiaId = iglesiaId;
    }
}
