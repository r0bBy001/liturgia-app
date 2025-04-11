package com.example.demo.galeria.model;

import com.example.demo.Iglesia.model.Iglesia;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Galeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "iglesia_id", nullable = false)
    private Iglesia iglesia;

    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "galeria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenGaleria> imagenes;

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

    public Iglesia getIglesia() {
        return iglesia;
    }

    public void setIglesia(Iglesia iglesia) {
        this.iglesia = iglesia;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ImagenGaleria> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenGaleria> imagenes) {
        this.imagenes = imagenes;
    }
}