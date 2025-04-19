package com.example.demo.usuario.model;

import jakarta.persistence.*;

import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.model.Rol;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "iglesia_id")
    private Iglesia iglesia;

    public Usuario(){}

    public Usuario(String username, String password, Rol rol, Iglesia iglesia){
        this.username = username;
        this.password = password; 
        this.rol = rol;
        this.iglesia = iglesia;
        validarRelacionConIglesia();
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        this.password = password;
    }

    @PrePersist
    @PreUpdate
    private void validarRelacionConIglesia() {
    }

    public String getPassword(){
        return password;
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
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    public Iglesia getIglesia() {
        return iglesia;
    }
    public void setIglesia(Iglesia iglesia) {
        this.iglesia = iglesia;
    }
}
