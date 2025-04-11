package com.example.demo.banner.model;

import jakarta.persistence.*;
import com.example.demo.model.TipoBanner;

@Entity
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;

    private boolean activo;

    @Enumerated(EnumType.STRING)
    private TipoBanner tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public TipoBanner getTipo() {
        return tipo;
    }

    public void setTipo(TipoBanner tipo) {
        this.tipo = tipo;
    }
}
