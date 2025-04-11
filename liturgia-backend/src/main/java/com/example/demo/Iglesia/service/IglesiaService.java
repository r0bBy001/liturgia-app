package com.example.demo.Iglesia.service;

import com.example.demo.Iglesia.dto.IglesiaDTO;
import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.Iglesia.repository.IglesiaRepository;
import com.example.demo.imagen.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class IglesiaService {

    private final IglesiaRepository iglesiaRepository;
    private final ImageService imageService;

    public IglesiaService(IglesiaRepository iglesiaRepository, ImageService imageService){
        this.iglesiaRepository = iglesiaRepository;
        this.imageService = imageService;
    }

    public List<Iglesia> listarIglesias(){
        return iglesiaRepository.findAll();
    }

    public Iglesia guardarIglesia(IglesiaDTO dto, MultipartFile imagen, MultipartFile portada) throws IOException {
        Iglesia iglesia = new Iglesia();
        iglesia.setNombre(dto.getNombre());
        iglesia.setDireccion(dto.getDireccion());
        iglesia.setCiudad(dto.getCiudad());
        iglesia.setTelefono(dto.getTelefono());
        iglesia.setCorreo(dto.getCorreo());

        try {
            if (imagen != null && !imagen.isEmpty()) {
                String nombreImagen = imageService.saveImage(imagen, "iglesia");
                iglesia.setImagen(nombreImagen);
            }

            if (portada != null && !portada.isEmpty()) {
                String nombrePortada = imageService.saveImage(portada, "iglesia/portadas");
                iglesia.setPortada(nombrePortada);
            }
        } catch (IllegalArgumentException | IOException e) {
            throw new RuntimeException("Error al procesar las imágenes: " + e.getMessage(), e);
        }

        return iglesiaRepository.save(iglesia);
    }

    public Iglesia obtenerIglesiaPorID(Long id){
        return iglesiaRepository.findById(id).orElse(null);
    }

    public void eliminarIglesia(Long id){
        iglesiaRepository.deleteById(id);
    }

    public Iglesia actualizarIglesia(Long id, IglesiaDTO dto, MultipartFile nuevaImagen, MultipartFile nuevaPortada) throws IOException {
        return iglesiaRepository.findById(id).map(iglesia -> {
            iglesia.setNombre(dto.getNombre());
            iglesia.setDireccion(dto.getDireccion());
            iglesia.setCiudad(dto.getCiudad());
            iglesia.setTelefono(dto.getTelefono());
            iglesia.setCorreo(dto.getCorreo());

            try {
                if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
                    String nombreImagen = imageService.saveImage(nuevaImagen, "iglesia");
                    iglesia.setImagen(nombreImagen);
                }

                if (nuevaPortada != null && !nuevaPortada.isEmpty()) {
                    String nombrePortada = imageService.saveImage(nuevaPortada, "iglesia/portadas");
                    iglesia.setPortada(nombrePortada);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar imagen o portada", e);
            }

            return iglesiaRepository.save(iglesia);
        }).orElse(null);
    }
}
