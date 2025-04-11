package com.example.demo.acto_liturgico.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.demo.tipo_acto_liturgico.model.TipoActoLiturgico;
import com.example.demo.Iglesia.model.Iglesia;

@Entity
public class ActoLiturgico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_acto_id")
    private TipoActoLiturgico tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "iglesia_id")
    private Iglesia iglesia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public TipoActoLiturgico getTipo() {
        return tipo;
    }

    public void setTipo(TipoActoLiturgico tipo) {
        this.tipo = tipo;
    }

    public Iglesia getIglesia() {
        return iglesia;
    }

    public void setIglesia(Iglesia iglesia) {
        this.iglesia = iglesia;
    }
}
