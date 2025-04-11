package com.example.demo.contacto.model;

import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.model.TipoContacto;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    @Lob
    private String mensaje;

    private LocalDate fechaEnvio;

    @Enumerated(EnumType.STRING)
    private TipoContacto tipo;

    @ManyToOne
    @JoinColumn(name = "iglesia_id", nullable = true)
    private Iglesia iglesia;

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

    public TipoContacto getTipo() {
        return tipo;
    }

    public void setTipo(TipoContacto tipo) {
        this.tipo = tipo;
    }

    public Iglesia getIglesia() {
        return iglesia;
    }

    public void setIglesia(Iglesia iglesia) {
        this.iglesia = iglesia;
    }
}
