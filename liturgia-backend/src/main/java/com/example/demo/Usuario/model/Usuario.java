package com.example.demo.Usuario.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

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
        this.setPassword(password);
        this.rol = rol;
        this.iglesia = iglesia;
        validarRelacionConIglesia();
    }
    private String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public void setPassword(String password){
        this.password=hashPassword(password);
    }

    @PrePersist
    @PreUpdate
    private void validarRelacionConIglesia(){
        if(this.rol == Rol.ENCARGADO && this.iglesia == null){
            throw new IllegalArgumentException("Un encargado debe tener una iglesia asignada");
        }else if(this.rol == Rol.SUPERADMIN && this.iglesia !=null){
            throw new IllegalArgumentException("Un SUPERADMIN no debe estar asignado a una iglesia");
        }
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
