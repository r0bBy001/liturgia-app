package com.example.demo.contacto.controller;

import com.example.demo.contacto.model.Contacto;
import com.example.demo.contacto.service.ContactoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping
    public ResponseEntity<List<Contacto>> listarContactos() {
        return ResponseEntity.ok(contactoService.listarContactos());
    }

    @GetMapping("/iglesia/{iglesiaId}")
    public ResponseEntity<List<Contacto>> listarContactosPorIglesia(@PathVariable Long iglesiaId) {
        return ResponseEntity.ok(contactoService.listarContactosPorIglesia(iglesiaId));
    }

    @PostMapping
    public ResponseEntity<Contacto> guardarContacto(@RequestBody Contacto contacto) {
        Contacto nuevoContacto = contactoService.guardarContacto(contacto);
        return ResponseEntity.ok(nuevoContacto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtenerContactoPorId(@PathVariable Long id) {
        Contacto contacto = contactoService.obtenerContactoPorId(id);
        if (contacto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contacto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContacto(@PathVariable Long id) {
        contactoService.eliminarContacto(id);
        return ResponseEntity.noContent().build();
    }
}
