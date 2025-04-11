package com.example.demo.galeria.model;

import jakarta.persistence.*;

@Entity
public class ImagenGaleria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "galeria_id", nullable = false)
    private Galeria galeria;

    private String url;

    private boolean esPortada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Galeria getGaleria() {
        return galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEsPortada() {
        return esPortada;
    }

    public void setEsPortada(boolean esPortada) {
        this.esPortada = esPortada;
    }
}
