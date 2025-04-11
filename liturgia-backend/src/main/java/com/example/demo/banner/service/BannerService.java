package com.example.demo.banner.service;

import com.example.demo.banner.dto.BannerDTO;
import com.example.demo.banner.model.Banner;
import com.example.demo.banner.repository.BannerRepository;
import com.example.demo.imagen.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final ImageService imageService;

    public BannerService(BannerRepository bannerRepository, ImageService imageService) {
        this.bannerRepository = bannerRepository;
        this.imageService = imageService;
    }

    public List<Banner> listarBanners() {
        return bannerRepository.findAll();
    }

    public Banner guardarBanner(BannerDTO dto, MultipartFile imagen) throws IOException {
        Banner banner = new Banner();
        banner.setActivo(dto.isActivo());
        banner.setTipo(dto.getTipo());

        if (imagen != null && !imagen.isEmpty()) {
            String nombreImagen = imageService.saveImage(imagen, "banners");
            banner.setImagen(nombreImagen);
        }

        return bannerRepository.save(banner);
    }

    public Banner actualizarBanner(Long id, BannerDTO dto, MultipartFile nuevaImagen) throws IOException {
        return bannerRepository.findById(id).map(banner -> {
            banner.setActivo(dto.isActivo());
            banner.setTipo(dto.getTipo());

            try {
                if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
                    String nombreImagen = imageService.saveImage(nuevaImagen, "banners");
                    banner.setImagen(nombreImagen);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen del banner", e);
            }

            return bannerRepository.save(banner);
        }).orElse(null);
    }

    public Banner obtenerBannerPorId(Long id) {
        return bannerRepository.findById(id).orElse(null);
    }

    public void eliminarBanner(Long id) {
        bannerRepository.deleteById(id);
    }
}
