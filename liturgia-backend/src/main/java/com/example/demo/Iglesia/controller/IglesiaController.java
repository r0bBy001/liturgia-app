package com.example.demo.Iglesia.controller;

import com.example.demo.Iglesia.dto.IglesiaDTO;
import com.example.demo.Iglesia.model.Iglesia;
import com.example.demo.Iglesia.service.IglesiaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/iglesias")
@CrossOrigin(origins = "*")
public class IglesiaController {

    private final IglesiaService iglesiaService;

    public IglesiaController(IglesiaService iglesiaService) {
        this.iglesiaService = iglesiaService;
    }

    // Listar todas las iglesias
    @GetMapping
    public List<Iglesia> listar() {
        return iglesiaService.listarIglesias();
    }

    // Guardar una nueva iglesia
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Iglesia guardar(
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestPart(name = "imagen", required = false) MultipartFile imagen,
            @RequestPart(name = "portada", required = false) MultipartFile portada) throws IOException {

        // Crear el DTO manualmente con los parámetros recibidos
        IglesiaDTO dto = new IglesiaDTO();
        dto.setNombre(nombre);
        dto.setDireccion(direccion);
        dto.setCiudad(ciudad);
        dto.setTelefono(telefono);
        dto.setCorreo(correo);

        return iglesiaService.guardarIglesia(dto, imagen, portada);
    }

    // Obtener una iglesia por ID
    @GetMapping("/{id}")
    public Iglesia obtenerPorId(@PathVariable Long id) {
        return iglesiaService.obtenerIglesiaPorID(id);
    }

    // Actualizar una iglesia existente
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Iglesia actualizar(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestPart(name = "imagen", required = false) MultipartFile nuevaImagen,
            @RequestPart(name = "portada", required = false) MultipartFile nuevaPortada) throws IOException {

        // Crear el DTO manualmente con los parámetros recibidos
        IglesiaDTO dto = new IglesiaDTO();
        dto.setNombre(nombre);
        dto.setDireccion(direccion);
        dto.setCiudad(ciudad);
        dto.setTelefono(telefono);
        dto.setCorreo(correo);

        return iglesiaService.actualizarIglesia(id, dto, nuevaImagen, nuevaPortada);
    }

    // Eliminar una iglesia por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        iglesiaService.eliminarIglesia(id);
    }
}
