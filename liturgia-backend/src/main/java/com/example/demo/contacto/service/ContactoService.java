package com.example.demo.contacto.service;

import com.example.demo.contacto.model.Contacto;
import com.example.demo.contacto.repository.ContactoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public List<Contacto> listarContactos() {
        return contactoRepository.findAll();
    }

    public List<Contacto> listarContactosPorIglesia(Long iglesiaId) {
        return contactoRepository.findByIglesiaId(iglesiaId);
    }

    public Contacto guardarContacto(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public Contacto obtenerContactoPorId(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

    public void eliminarContacto(Long id) {
        contactoRepository.deleteById(id);
    }
}
