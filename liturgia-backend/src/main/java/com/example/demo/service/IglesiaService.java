package com.example.demo.service;

import com.example.demo.model.Iglesia;
import com.example.demo.repository.IglesiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IglesiaService {
    private final IglesiaRepository iglesiaRepository;
    public IglesiaService(IglesiaRepository iglesiaRepository){
        this.iglesiaRepository = iglesiaRepository;
    }

    public List<Iglesia> listarIglesias(){
        return iglesiaRepository.findAll();
    }

    public Iglesia guardarIglesia(Iglesia iglesia){
        return iglesiaRepository.save(iglesia);
    }

    public Iglesia obtenerIglesiaPorID(Long id){
        return iglesiaRepository.findById(id).orElse(null);
    }

    public void eliminarIglesia(Long id){
        iglesiaRepository.deleteById(id);
    }

    public Iglesia actualizarIglesia(Long id, Iglesia nuevaIglesia){
        return iglesiaRepository.findById(id).map(iglesia ->{
            iglesia.setNombre(nuevaIglesia.getNombre());
            iglesia.setDireccion(nuevaIglesia.getDireccion());
            iglesia.setCiudad(nuevaIglesia.getCiudad());
            iglesia.setTelefono(nuevaIglesia.getTelefono());
            iglesia.setCorreo(nuevaIglesia.getCorreo());
            return iglesiaRepository.save(iglesia);
        }).orElse(null);
    }
}
