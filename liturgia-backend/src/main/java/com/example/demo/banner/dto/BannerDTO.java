package com.example.demo.banner.dto;

import com.example.demo.model.TipoBanner;

public class BannerDTO {

    private Long id;
    private String imagen;
    private boolean activo;
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
