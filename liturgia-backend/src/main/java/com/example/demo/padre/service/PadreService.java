package com.example.demo.padre.service;

import com.example.demo.padre.model.Padre;
import com.example.demo.padre.repository.PadreRepository;
import com.example.demo.imagen.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PadreService {

    private final PadreRepository padreRepository;
    private final ImageService imageService;

    public PadreService(PadreRepository padreRepository, ImageService imageService) {
        this.padreRepository = padreRepository;
        this.imageService = imageService;
    }

    public List<Padre> listarPadres() {
        return padreRepository.findAll();
    }

    public List<Padre> listarPadresPorIglesia(Long iglesiaId) {
        return padreRepository.findByIglesiaId(iglesiaId);
    }

    public Padre guardarPadre(Padre padre, MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            String urlFoto = imageService.saveImage(foto, "padres");
            padre.setFoto(urlFoto);
        }
        return padreRepository.save(padre);
    }

    public Padre obtenerPadrePorId(Long id) {
        return padreRepository.findById(id).orElse(null);
    }

    public Padre actualizarPadre(Long id, Padre padre, MultipartFile nuevaFoto) throws IOException {
        return padreRepository.findById(id).map(padreExistente -> {
            padreExistente.setNombres(padre.getNombres());
            padreExistente.setApellidos(padre.getApellidos());
            padreExistente.setDescripcion(padre.getDescripcion());
            padreExistente.setIglesia(padre.getIglesia());
            try {
                if (nuevaFoto != null && !nuevaFoto.isEmpty()) {
                    String urlFoto = imageService.saveImage(nuevaFoto, "padres");
                    padreExistente.setFoto(urlFoto);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva foto", e);
            }
            return padreRepository.save(padreExistente);
        }).orElse(null);
    }

    public void eliminarPadre(Long id) {
        padreRepository.deleteById(id);
    }
}
