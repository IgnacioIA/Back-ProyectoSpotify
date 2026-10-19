package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
@CrossOrigin(origins = "*")
public class ArtistaController {

    @Autowired
    private ArtistaRepository artistaRepository;

    @PostMapping
    public Artista crearArtista(@RequestBody Artista artista) {
        return artistaRepository.save(artista);
    }

    @GetMapping
    public List<Artista> obtenerTodos() {
        return artistaRepository.findAll();
    }

    @GetMapping("/favoritos")
    public List<Artista> obtenerFavoritos() {
        return artistaRepository.findByFavoritoTrue();
    }

    @PatchMapping("/{id}/favorito")
    public ResponseEntity<Artista> alternarFavorito(@PathVariable Long id) {
        return artistaRepository.findById(id)
                .map(artista -> {
                    artista.setFavorito(!artista.getFavorito());
                    Artista artistaActualizado = artistaRepository.save(artista);
                    return ResponseEntity.ok(artistaActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
