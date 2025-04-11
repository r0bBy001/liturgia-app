package com.example.demo.galeria.dto;

public class ImagenGaleriaDTO {

    private Long id;
    private Long galeriaId;
    private String url;
    private boolean esPortada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGaleriaId() {
        return galeriaId;
    }

    public void setGaleriaId(Long galeriaId) {
        this.galeriaId = galeriaId;
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
