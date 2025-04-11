package com.example.demo.banner.controller;

import com.example.demo.banner.dto.BannerDTO;
import com.example.demo.banner.model.Banner;
import com.example.demo.banner.service.BannerService;
import com.example.demo.model.TipoBanner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public ResponseEntity<List<Banner>> listarBanners() {
        return ResponseEntity.ok(bannerService.listarBanners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banner> obtenerBannerPorId(@PathVariable Long id) {
        Banner banner = bannerService.obtenerBannerPorId(id);
        if (banner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(banner);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Banner> guardarBanner(
            @RequestParam("activo") boolean activo,
            @RequestParam("tipo") String tipo,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Crear el DTO manualmente con los parámetros recibidos
            BannerDTO dto = new BannerDTO();
            dto.setActivo(activo);
            dto.setTipo(TipoBanner.valueOf(tipo.toUpperCase())); // Convertir el tipo a enum

            Banner banner = bannerService.guardarBanner(dto, imagen);
            return ResponseEntity.ok(banner);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Tipo inválido
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null); // Error al guardar la imagen
        }
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Banner> actualizarBanner(
            @PathVariable Long id,
            @RequestParam("activo") boolean activo,
            @RequestParam("tipo") String tipo,
            @RequestPart(value = "nuevaImagen", required = false) MultipartFile nuevaImagen) {
        try {
            // Crear el DTO manualmente con los parámetros recibidos
            BannerDTO dto = new BannerDTO();
            dto.setActivo(activo);
            dto.setTipo(TipoBanner.valueOf(tipo.toUpperCase())); // Convertir el tipo a enum

            Banner banner = bannerService.actualizarBanner(id, dto, nuevaImagen);
            if (banner == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(banner);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Tipo inválido
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null); // Error al guardar la imagen
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBanner(@PathVariable Long id) {
        bannerService.eliminarBanner(id);
        return ResponseEntity.noContent().build();
    }
}
