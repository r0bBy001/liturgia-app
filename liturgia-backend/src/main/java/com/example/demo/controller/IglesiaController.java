package com.example.demo.controller;

import com.example.demo.model.Iglesia;
import com.example.demo.service.IglesiaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iglesias")
@CrossOrigin(origins = "*")
public class IglesiaController {
    private final IglesiaService iglesiaService;

    public IglesiaController(IglesiaService iglesiaService){
        this.iglesiaService=iglesiaService;
    }

    @GetMapping
    public List<Iglesia> lsitar(){
        return iglesiaService.listarIglesias();
    }

    @PostMapping
    public Iglesia guardar(@RequestBody Iglesia iglesia){
        return iglesiaService.guardarIglesia(iglesia);
    }

    @GetMapping("/{id}")
    public Iglesia obtenerPorId(@PathVariable Long id){
        return iglesiaService.obtenerIglesiaPorID(id);
    }

    @PutMapping("/{id}")
    public Iglesia actualizar(@PathVariable Long id, @RequestBody Iglesia nuevaIglesia){
        return iglesiaService.actualizarIglesia(id, nuevaIglesia);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        iglesiaService.eliminarIglesia(id);
    }
}
