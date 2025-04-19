package com.example.demo.imagen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public String saveImage(MultipartFile file, String entity) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen está vacío o es nulo.");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.matches(".*\\.(png|jpg|jpeg|gif)$")) {
            throw new IllegalArgumentException("Formato de archivo no soportado. Solo se permiten PNG, JPG, JPEG y GIF.");
        }

        String uploadsDir = System.getProperty("user.dir") + "/uploads/" + entity;
        Path uploadPath = Paths.get(uploadsDir);
        Files.createDirectories(uploadPath);

        logger.info("Ruta de la carpeta de destino: {}", uploadPath.toString());

        String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path filePath = uploadPath.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), filePath);

        logger.info("Archivo guardado exitosamente: {}", filePath.toString());

        return uniqueFileName;
    }
}
