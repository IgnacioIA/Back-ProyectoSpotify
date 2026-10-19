package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/canciones")
@CrossOrigin(origins = "*")
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @PostMapping
    public Cancion crearCancion(@RequestBody Cancion cancion) {
        if (cancion.getArtista() != null && cancion.getArtista().getId() != null) {
            Artista artista = artistaRepository.findById(cancion.getArtista().getId()).orElse(null);
            cancion.setArtista(artista);
        }
        return cancionRepository.save(cancion);
    }

    @GetMapping
    public List<Cancion> obtenerCanciones(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) String genero) { 
        
        if (titulo != null && !titulo.isEmpty()) {
            return cancionRepository.findByTituloContainingIgnoreCase(titulo);
        }
        if (artista != null && !artista.isEmpty()) {
            return cancionRepository.findByArtistaNombreContainingIgnoreCase(artista);
        }
        if (genero != null && !genero.isEmpty()) {
            return cancionRepository.findByGeneroContainingIgnoreCase(genero); 
        }
        return cancionRepository.findAll();
    }

    @GetMapping("/favoritas")
    public List<Cancion> obtenerFavoritas() {
        return cancionRepository.findByFavoritoTrue();
    }

    @PatchMapping("/{id}/favorito")
    public ResponseEntity<Cancion> alternarFavorito(@PathVariable Long id) {
        return cancionRepository.findById(id)
                .map(cancion -> {
                    cancion.setFavorito(!cancion.getFavorito());
                    Cancion cancionActualizada = cancionRepository.save(cancion);
                    return ResponseEntity.ok(cancionActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }


}