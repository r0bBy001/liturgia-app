package com.example.demo.noticia.service;

import com.example.demo.noticia.dto.NoticiaDTO;
import com.example.demo.noticia.model.Noticia;
import com.example.demo.noticia.repository.NoticiaRepository;
import com.example.demo.imagen.service.ImageService;
import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.Iglesia.repository.IglesiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final IglesiaRepository iglesiaRepository;
    private final ImageService imageService;

    public NoticiaService(NoticiaRepository noticiaRepository, IglesiaRepository iglesiaRepository, ImageService imageService) {
        this.noticiaRepository = noticiaRepository;
        this.iglesiaRepository = iglesiaRepository;
        this.imageService = imageService;
    }

    public List<Noticia> listarNoticias() {
        return noticiaRepository.findAll();
    }

    public Noticia guardarNoticia(NoticiaDTO dto, MultipartFile imagenPortada) throws IOException {
        Noticia noticia = new Noticia();
        noticia.setTitulo(dto.getTitulo());
        noticia.setContenido(dto.getContenido());
        noticia.setFechaPublicacion(dto.getFechaPublicacion());

        if (dto.getIglesiaId() != null) {
            Optional<Iglesia> iglesia = iglesiaRepository.findById(dto.getIglesiaId());
            iglesia.ifPresent(noticia::setIglesia);
        }

        if (imagenPortada != null && !imagenPortada.isEmpty()) {
            String nombreImagen = imageService.saveImage(imagenPortada, "noticias");
            noticia.setImagenPortada(nombreImagen);
        }

        return noticiaRepository.save(noticia);
    }

    public Noticia obtenerNoticiaPorId(Long id) {
        return noticiaRepository.findById(id).orElse(null);
    }

    public void eliminarNoticia(Long id) {
        noticiaRepository.deleteById(id);
    }

    public Noticia actualizarNoticia(Long id, NoticiaDTO dto, MultipartFile nuevaImagenPortada) throws IOException {
        return noticiaRepository.findById(id).map(noticia -> {
            noticia.setTitulo(dto.getTitulo());
            noticia.setContenido(dto.getContenido());
            noticia.setFechaPublicacion(dto.getFechaPublicacion());

            if (dto.getIglesiaId() != null) {
                Optional<Iglesia> iglesia = iglesiaRepository.findById(dto.getIglesiaId());
                iglesia.ifPresent(noticia::setIglesia);
            }

            try {
                if (nuevaImagenPortada != null && !nuevaImagenPortada.isEmpty()) {
                    String nombreImagen = imageService.saveImage(nuevaImagenPortada, "noticias");
                    noticia.setImagenPortada(nombreImagen);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen de portada", e);
            }

            return noticiaRepository.save(noticia);
        }).orElse(null);
    }
}
