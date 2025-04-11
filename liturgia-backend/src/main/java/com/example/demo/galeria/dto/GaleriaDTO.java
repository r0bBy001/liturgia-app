package com.example.demo.galeria.dto;

import java.time.LocalDate;
import java.util.List;

public class GaleriaDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private Long iglesiaId; 
    private LocalDate fechaCreacion;
    private List<ImagenGaleriaDTO> imagenes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(Long iglesiaId) {
        this.iglesiaId = iglesiaId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ImagenGaleriaDTO> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenGaleriaDTO> imagenes) {
        this.imagenes = imagenes;
    }
}
