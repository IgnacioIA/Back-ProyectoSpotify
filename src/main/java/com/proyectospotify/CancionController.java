package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canciones")
@CrossOrigin(origins = "*")
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;

    @GetMapping
    public List<Cancion> obtenerCanciones(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) String genero) { 
        
        if (titulo != null && !titulo.isEmpty()) {
            return cancionRepository.findByTituloContainingIgnoreCase(titulo);
        }
        if (artista != null && !artista.isEmpty()) {
            return cancionRepository.findByArtistaContainingIgnoreCase(artista);
        }
        if (genero != null && !genero.isEmpty()) {
            return cancionRepository.findByGeneroContainingIgnoreCase(genero); 
        }
        return cancionRepository.findAll();
    }

    @PostMapping
    public Cancion guardarCancion(@RequestBody Cancion cancion) {
        return cancionRepository.save(cancion);
    }
}